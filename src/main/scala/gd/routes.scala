package gd

import java.io.File

import zio.*
import zio.http.*
import zio.json.*

import gd.services.seedcompany.SeedCompanies

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

def apis(seedCompanies: SeedCompanies) = Routes(
  Method.GET / "api" / "seed-companies" ->
    handler(
      seedCompanies.list
        .map(companies => Response.json(companies.toJson))
        .resurrect
        .catchAll { e =>
          {
            println(s"e: ${e.printStackTrace()}")
            ZIO.succeed(Response.json("asdf"))
          }
        },
    ),
)

def routes(seedCompanies: SeedCompanies) =
  pages ++ assets ++ apis(seedCompanies)

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
