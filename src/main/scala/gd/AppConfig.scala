package gd

import java.io.File

import zio.*
import zio.Config
import zio.Config.*
import zio.config.*
import zio.config.magnolia.*
import zio.config.typesafe.*

enum Env(name: String) extends RichEnum(name):
  case Dev extends Env("dev")
  case Test extends Env("test")
  case Prod extends Env("prod")

case class EnvConfig(
    env: Env,
)

object EnvConfig:
  private val path = "ENV"
  private val config = string(path)
    .mapOrFail { raw =>
      raw.toLowerCase match
        case Env.Dev(dev)   => Right(dev)
        case Env.Test(test) => Right(test)
        case Env.Prod(prod) => Right(prod)
        case _ =>
          Left(
            Config.Error
              .InvalidData(Chunk(path), s"Invalid environment: ${raw}"),
          )
    }
    .to[EnvConfig]

  def load: IO[Config.Error, EnvConfig] =
    ConfigProvider.envProvider.load(config)

// TODO: More typesafe values + nested configs
case class AppConfig(
    port: Int,
    database: DatabaseConfig,
)

case class DatabaseConfig(
    host: String,
    port: Int,
    database: String,
    user: String,
    password: String,
)

object AppConfig:

  def load(env: Env): IO[Config.Error, AppConfig] =
    val loader = AppConfig.getClass.getClassLoader
    val configFile =
      new File(
        loader
          .getResource(s"application.${env.getName.toLowerCase}.conf")
          .getPath,
      )
    val source = ConfigProvider.envProvider.orElse(
      TypesafeConfigProvider.fromHoconFile(configFile),
    )
    source.load(deriveConfig[AppConfig])
