package gd.views

import zio.http.template.*

val index = layout("Home")(
  h1("Hello Templating"),
)
