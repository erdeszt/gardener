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
          Response(
            Status.InternalServerError,
            Headers.empty,
            Body.fromString("Ooops, something went wrong."),
          )
        }.toHttpApp @@ Middleware.debug, // TODO: .when(Env == Dev)
      )
      .provide(Server.defaultWithPort(8080))
