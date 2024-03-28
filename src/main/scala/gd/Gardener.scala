package gd

import org.flywaydb.core.Flyway
import zio.*
import zio.http.*

object Gardener extends ZIOAppDefault:

  override val run: Task[Unit] = for
    env <- EnvConfig.load.map(_.env)
    config <- AppConfig.load(env)
    _ <- runMigrations
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
  private def runMigrations =
    ZIO.attempt {
      Class.forName("org.postgresql.Driver")
      val flyway =
        Flyway
          .configure()
          .dataSource(
            "jdbc:postgresql://127.0.0.1:5432/postgres",
            "postgres",
            "dev",
          )
          .load()

      flyway.migrate()
    }
