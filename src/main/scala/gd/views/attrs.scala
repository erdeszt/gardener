package gd.views

import zio.http.template.Attributes.PartialAttribute
import zio.http.template.{Html, classAttr}

def classes(cls: String*): Html = classAttr := List(cls*)

val integrityAttr: PartialAttribute[String] = PartialAttribute("integrity")
val crossoriginAttr: PartialAttribute[String] = PartialAttribute("crossorigin")
val roleAttr: PartialAttribute[String] = PartialAttribute("role")

val dataBsToggle: PartialAttribute[String] = PartialAttribute(
  "data-bs-toggle",
)

val dataBsTarget: PartialAttribute[String] = PartialAttribute("data-bs-target")
val ariaControls: PartialAttribute[String] = PartialAttribute("aria-controls")
val ariaExpanded: PartialAttribute[String] = PartialAttribute("aria-expanded")
val ariaLabel: PartialAttribute[String] = PartialAttribute("aria-label")
