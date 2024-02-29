package gd

import zio.*
import zio.http.*

object Gardener extends ZIOAppDefault:

  override val run: Task[Nothing] =
    Server
      .serve(
        routes.handleErrorCause { error =>
          println(s"Error: ${error}")
          // TODO: Wait for new RC release(handleErrorCauseZIO): ZIO.logErrorCause(s"Uncaught error: ${error.prettyPrint}", error) *>
          Response.internalServerError("Ooops, something went wrong.")
        }.toHttpApp @@ (Middleware.beautifyErrors ++ Middleware.debug),
      )
      .provide(Server.defaultWith(_.logWarningOnFatalError(true).port(8080)))
