public class A {
   
   public int a1;
   public int a2;

   public A(int u, int v) {
	 a1=u;
	 a2=v;
   }

   public void met1(int u, int v) {
	 a1 = a1 + u;
	 a2 = a2 + v;
   }

   public A met2(int u, int v) {
	 return new A(a1+u, a2+v);
   }

   public boolean egale(A other) {
	 return ((this.a1 == other.a1) && (this.a2 == other.a2));
   }

   public String toString() {
	 return ("("+ a1 + ","+ a2+")");
   }
}