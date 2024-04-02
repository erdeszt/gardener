package gd

import com.github.f4b6a3.ulid.Ulid
import org.flywaydb.core.Flyway
import zio.*
import zio.http.*
import zio.jdbc.{
  ZConnectionPool,
  ZConnectionPoolConfig,
  sqlInterpolator,
  transaction,
}

import gd.services.seedcompany.SeedCompaniesLive
import gd.services.seedcompany.internal.SeedCompaniesRepoLive

// TODO: Switch to skunk
object Gardener extends ZIOAppDefault:

  val connectionPoolConfig: ULayer[ZConnectionPoolConfig] =
    ZLayer.succeed(ZConnectionPoolConfig.default)
  def connectionPool(
      config: DatabaseConfig,
  ): ZLayer[ZConnectionPoolConfig, Nothing, ZConnectionPool] =
    ZConnectionPool
      .postgres(
        config.host,
        config.port,
        config.database,
        Map(
          "user" -> config.user,
          "password" -> config.password,
        ),
      )
      .orDie

  // TODO: Cleanup injection and service/layer creation
  override val run: Task[Unit] = for
    env <- EnvConfig.load.map(_.env)
    _ = Ulid.fast()
    repo = SeedCompaniesRepoLive()
    config <- AppConfig.load(env)
    _ <- runMigrations(config.database)
    _ <- ZIO.logInfo(s"Running at port: ${config.port}")
    _ <- (for
      pool <- ZIO
        .service[ZConnectionPool]
//      _ <- transaction(sql"delete from seed_company".execute.unit)
//      _ <- transaction(repo.insert("test"))
      xs <- transaction(repo.list)
      _ = println(s"Companies: ${xs}")
      services = SeedCompaniesLive(pool, repo)
      app = routes(services).handleErrorCause { error =>
        println(s"Error: ${error}")
        // TODO: Wait for new RC release(handleErrorCauseZIO): ZIO.logErrorCause(s"Uncaught error: ${error.prettyPrint}", error) *>
        Response(
          Status.InternalServerError,
          Headers.empty,
          Body.fromString("Ooops, something went wrong."),
        )
      }.toHttpApp @@ Middleware.debug // TODO: .when(Env == Dev)
      _ <- Server
        .serve(app)
        .provide(
          Server.defaultWithPort(config.port),
        )
    yield ()).provide(connectionPoolConfig >>> connectionPool(config.database))
  yield ()

  // TODO: Move to service, use config
  private def runMigrations(config: DatabaseConfig) =
    ZIO.attempt {
      val flyway =
        Flyway
          .configure()
          .dataSource(
            s"jdbc:postgresql://${config.host}:${config.port}/${config.database}",
            config.user,
            config.password,
          )
          .load()

      flyway.migrate()
    }
