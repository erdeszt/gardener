package gd

abstract class RichEnum(private val name: String)
    extends Product
    with Serializable {
  self =>
  def unapply(raw: String): Option[self.type] = {
    if (raw == name) {
      Some(self)
    } else {
      None
    }
  }
  def getName: String = name
}
