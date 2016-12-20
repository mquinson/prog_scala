public class B {

   public A b1;
   public A b2;
   public A b3;

   public B(A x, A y, A z) {
	 b1=x;
	 b2=y;
	 b3=z;
   }

   public void met1(int u, int v) {
	 b1.met1(u,v);
	 b2.met1(u,v);
	 b3.met1(u,v);
   }

   public B met2(int u, int v) {
	 return new B(b1.met2(u,v), b2.met2(u,v), b3.met2(u,v));
   }

   public B met3(int u, int v) {
	 b1.met1(u,v);
	 b2.met1(u,v);
	 b3.met1(u,v);
	 return new B(b1,b2,b3);
   }

   public boolean egale(B other) {
	 return ( (this.b1).egale(other.b1) &&
	 	  (this.b2).egale(other.b2) &&
		  (this.b3).egale(other.b3) );
   }

   public String toString() {
	 return ("["+ b1 + ";"+ b2+";"+b3+"]") ;
   }
}