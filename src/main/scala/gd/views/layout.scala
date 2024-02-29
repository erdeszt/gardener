package gd.views

import zio.http.template.*

// TODO: Active page for navbar
// TODO: Reverse router
def layout(title: String)(content: Dom): Dom =
  html(
    head(
      meta(charsetAttr := "utf-8"),
      meta(
        nameAttr := "viewport",
        contentAttr := "width=device-width, initial-scale=1",
      ),
      zio.http.template.title(s"Gardener - ${title}"),
      link(
        relAttr := "shortcut icon",
        typeAttr := "image/png",
        hrefAttr := "/images/favicon.png",
      ),
      link(
        relAttr := "stylesheet",
        hrefAttr := "https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css",
        integrityAttr := "sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH",
        crossoriginAttr := "anonymous",
      ),
      link(
        relAttr := "stylesheet",
        mediaAttr := "screen",
        hrefAttr := "/stylesheets/main.css",
      ),
    ),
    body(
      nav(
        classes("navbar", "navbar-expand-lg"),
        div(
          classes("container-fluid"),
          a(classes("navbar-brand"), hrefAttr := "/", "Gardener"),
          button(
            classes("navbar-toggler"),
            typeAttr := "button",
            dataBsToggle := "collapse",
            dataBsTarget := "#navbarContent",
            ariaControls := "navbarContent",
            ariaExpanded := "false",
            ariaLabel := "Toggle navigation",
            span(classes("navbar-toggler-icon")),
          ),
          div(
            classes("collapse", "navbar-collapse"),
            idAttr := "navbarContent",
            ul(
              classes("navbar-nav", "me-auto", "mb-2", "mg-lg-0"),
              li(
                classes("navbar-item"),
                a(classes("nav-link"), hrefAttr := "/", "Home"),
              ),
              li(
                classes("nav-item", "dropdown"),
                a(
                  classes("nav-link", "dropdown-toggle"),
                  hrefAttr := "#",
                  roleAttr := "button",
                  dataBsToggle := "dropdown",
                  ariaExpanded := "false",
                  "Tools",
                ),
                ul(
                  classes("dropdown-menu"),
                  li(
                    a(
                      classes("dropdown-item"),
                      hrefAttr := "/tools/plant-database",
                      "Plant database",
                    ),
                  ),
                  li(
                    a(
                      classes("dropdown-item"),
                      hrefAttr := "/tools/companion-planner",
                      "Companion planner",
                    ),
                  ),
                ),
              ),
            ),
          ),
        ),
      ),
      div(classes("container-fluid"), content),
      script(
        srcAttr := "https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js",
        integrityAttr := "sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz",
        crossoriginAttr := "anonymous",
      ),
      script(srcAttr := "/javascript/main.js"),
    ),
  )
