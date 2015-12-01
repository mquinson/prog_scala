// Not everything compiles. So load it in the REPL as follows:
// scala> :load Beugnard.scala

class Top
class Middle extends Top
class Bottom extends Middle
class Up {
  def cv(t:Top) = "Up"
  def inv(m:Middle) = "Up"
  def ctv(b:Bottom) = "Up"
}
class Down extends Up {
  def cv(m:Middle) = "Down"
  override def inv(m:Middle) = "Down"
  def ctv(m:Middle) = "Down"
}
val u: Up  = new Up
val d: Down= new Down
val ud:Up  = new Down

println("First column");
print("u.cv(new Top)    :");println(u.cv(new Top))
print("u.cv(new Middle) :");println(u.cv(new Middle))
print("u.cv(new Bottom) :");println(u.cv(new Bottom))

print("u.inv(new Top)   :");println(u.inv(new Top))
print("u.inv(new Middle):");println(u.inv(new Middle))
print("u.inv(new Bottom):");println(u.inv(new Bottom))

print("u.ctv(new Top)   :");println(u.ctv(new Top))
print("u.ctv(new Middle):");println(u.ctv(new Middle))
print("u.ctv(new Bottom):");println(u.ctv(new Bottom))

println("XXXXXXXXXXXXXXXXXXXX")
println("Second column");
println("d.cv(new Top)    :");println(d.cv(new Top))
println("d.cv(new Middle) :");println(d.cv(new Middle))
println("d.cv(new Bottom) :");println(d.cv(new Bottom))

println("d.inv(new Top)   :");println(d.inv(new Top))
println("d.inv(new Middle):");println(d.inv(new Middle))
println("d.inv(new Bottom):");println(d.inv(new Bottom))

println("d.ctv(new Top)   :");println(d.ctv(new Top))
println("d.ctv(new Middle):");println(d.ctv(new Middle))
println("d.ctv(new Bottom):");println(d.ctv(new Bottom))

println("XXXXXXXXXXXXXXXXXXXX")
println("Third column");
println("ud.cv(new Top)    :");println(ud.cv(new Top))
println("ud.cv(new Middle) :");println(ud.cv(new Middle))
println("ud.cv(new Bottom) :");println(ud.cv(new Bottom))

println("ud.inv(new Top)   :");println(ud.inv(new Top))
println("ud.inv(new Middle):");println(ud.inv(new Middle))
println("ud.inv(new Bottom):");println(ud.inv(new Bottom))

println("ud.ctv(new Top)   :");println(ud.ctv(new Top))
println("ud.ctv(new Middle):");println(ud.ctv(new Middle))
println("ud.ctv(new Bottom):");println(ud.ctv(new Bottom))
