class ObjectA(u:Int, v:Int) {
   var a1 = u
   var a2 = v

   def action1(u:Int, v:Int) = {
       a1 = a1 + u
       a2 = a2 + v
   }
   def action2(u:Int, v:Int) = new ObjectA(a1+u, a2+v)
   override def toString() = "("+a1+" ; "+a2+")"
}

var n1 = 2
var n2 = 6
val a1 = new ObjectA(n1, n2)
val a2 = a1.action2(15, 9)
a1.action1(15, 9)
n1 = n1 + n2
n2 = n1 + n2
val a3 = new ObjectA(n1, n2)
a3.action1(9, 15)
// bla
println("Q1a1:"+a1)
println("Q1a2:"+a2)
println("Q1a3:"+a3)

class ObjectB(b1:ObjectA, b2:ObjectA) {
   def action1(u:Int, v:Int): Unit = {
     b1.action1(u,v)
     b2.action2(u,v)
   }
   def action2(u:Int, v:Int) =
     new ObjectB(b1.action2(u,v), b2.action2(u,v))
   def action3(u:Int, v:Int) = {
     b1.action1(u,v)
     b2.action2(u,v)
     new ObjectB(b1,b2)     
   }
   override def toString() = "["+b1+" ; "+b2+"]"
}

var A1 = new ObjectA(1,5)
var A2 = new ObjectA(6,7)
var B1 = new ObjectB(A1,A2)
B1.action1(20,10)
val B2 = B1.action2(10,5)

A1 = new ObjectA(3,4)
A2 = new ObjectA(2,5)
val B3 = new ObjectB(new ObjectA(3,4), new ObjectA(2,5))
B1.action1(5,10)
val B4 = B2.action3(50,100)

println("Q2A1:"+ A1);
println("Q2A2:"+ A2);

println("Q2B1:"+ B1);
println("Q2B2:"+ B2);
println("Q2B3:"+ B3);
println("Q2B4:"+ B4);
