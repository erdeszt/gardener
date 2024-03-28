package gd

import org.flywaydb.core.Flyway
import zio.*
import zio.http.*

object Gardener extends ZIOAppDefault:

  override val run: Task[Unit] = for
    env <- EnvConfig.load.map(_.env)
    config <- AppConfig.load(env)
    _ <- runMigrations(config.database)
    _ <- ZIO.logInfo(s"Running at port: ${config.port}")
    _ <- Server
      .serve(
        routes.handleErrorCause { error =>
          println(s"Error: ${error}")
          // TODO: Wait for new RC release(handleErrorCauseZIO): ZIO.logErrorCause(s"Uncaught error: ${error.prettyPrint}", error) *>
          Response(
            Status.InternalServerError,
            Headers.empty,
            Body.fromString("Ooops, something went wrong."),
          )
        }.toHttpApp @@ Middleware.debug, // TODO: .when(Env == Dev)
      )
      .provide(Server.defaultWithPort(config.port))
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
