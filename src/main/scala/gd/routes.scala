package gd

import java.io.File

import zio.*
import zio.http.*

val pages: Routes[Any, Nothing] = Routes(
  Method.GET / "" -> handler(Response.html(views.index)),
)

val assets =
  given ClassLoader = Gardener.getClass.getClassLoader
  Routes(
    Method.GET / "images" / string("image") ->
      handler { (image: String, request: Request) =>
        image.split('.') match
          case Array(name, extension) =>
            for
              mediaType <- ZIO
                .fromOption(MediaType.forFileExtension(extension))
                .orElseFail(InvalidStaticAssetExtensionError(extension))
              response <- getStaticAssetFromResource(
                s"static/images/${image}",
                mediaType,
              )
            yield response
          case _ => ZIO.succeed(Response.badRequest("Invalid image url"))
      },
    Method.GET / "stylesheets" / string("stylesheet") ->
      handler { (stylesheet: String, request: Request) =>
        getStaticAssetFromResource(
          s"static/stylesheets/${stylesheet}",
          MediaType.text.css,
        ).orDie
      },
    Method.GET / "javascript" / string("javascript") ->
      handler { (javascript: String, request: Request) =>
        getStaticAssetFromResource(
          s"static/javascripts/${javascript}",
          MediaType.application.javascript,
        ).orDie
      },
  )

val apis = Routes(
  Method.GET / "api" / "seed-companies" ->
    handler(ZIO.succeed(Response.text("WAAT"))),
)

val routes = pages ++ assets ++ apis

case class InvalidStaticAssetExtensionError(extension: String)
    extends RuntimeException(
      s"Invalid extension for static asset: ${extension}",
    )

private def getStaticAssetFromResource(
    path: String,
    mediaType: MediaType,
)(using classLoader: ClassLoader): Task[Response] =
  for
    url <- ZIO.attemptBlocking(classLoader.getResource(path))
    file <- ZIO.attempt(new File(url.getPath))
  yield Response(
    Status.Ok,
    Headers(
      Header.ContentType(mediaType),
      Header.ContentLength(file.length()),
    ),
    Body.fromFile(file),
  )
