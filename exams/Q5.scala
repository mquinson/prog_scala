class Fleur {
  def odeur() = println("Fleur")
}
class Rose extends Fleur {
  override def odeur() = println("Rose")
}
val f1:Fleur = new Rose()
f1.odeur()
val f2:Fleur = new Fleur()
f2.odeur()
val f3:Rose   = new Rose()
f3.odeur()