
class Book(t:String) {
  val title = t
  def equals(b:Book): Boolean = (title == b.title)
}

val name1 = "The Hitchhikers Guide to the Galaxy"
val name2 = "Mostly Harmless"
val b1 = new Book(name1)
val b2 = new Book(name1)
val b3 = new Book(name2)
print(b1.equals(b2) + ":")
print((b1 == b2) + ":")
print(b1.equals(b3) + ":")
print(b1 == b3)
       