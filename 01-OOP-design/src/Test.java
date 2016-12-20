public class Test {

   public static void main(String[] args) {
 	 int n1;
	 int n2;
	 A p1, p2, p3;
	 B f1, f2, f3;

	 n1 = 1;
	 n2 = 7;
	 p1 = new A(n1,n2);
	 System.out.println("p1="+p1);
	 p2 = p1.met2(28,11);
	 p1.met1(28,11);
	 System.out.println("p1="+p1);
	 System.out.println("p2 (ou p1)="+p2);
	 System.out.println(p1==p2);
	 System.out.println(p1.egale(p2));
	 n1 = n1 + n2;
	 n2 = n1 + n2;
	 p3 = new A (n1, n2);
	 System.out.println("p3 (ou p2)="+p3);
	 p3.met1(21,3);
	 System.out.println("p3 (ou p2)="+p3);
	 System.out.println(p1 == p3);
	 System.out.println(p1.egale(p3));
	 p1=p2;
	 System.out.println(p1==p2);
	 System.out.println(p1.egale(p2));
	 System.out.println("");

	 // point_1
	 System.out.println("---- POINT 1 ----");

	 p1 = new A(1,4);
	 p2 = new A(5,7);
	 p3 = new A(8,9);
	 f1 = new B(p1,p2,p3);
	 System.out.println("f1="+f1);
	 f1.met1(10,20);
	 System.out.println("f1="+f1);
	 f2 = f1.met2(5,10);
	 System.out.println("f1="+f1);
	 System.out.println("f2="+f2);
	 System.out.println(f1==f2);
	 f1.met1(5,10);
	 System.out.println("f1="+f1);
	 System.out.println(f1==f2);
	 System.out.println(f1.egale(f2));
	 System.out.println("");

	 // point_2
	 System.out.println("---- POINT 2 ----");

	 p1 = new A(2,4);
	 p2 = new A(3,6);
	 p3 = new A(4,8);
	 f1 = new B(p1,p2,p1);
	 f2 = new B(new A(2,4), new A(3,6), new A(2,4));
	 System.out.println(f1==f2);
	 System.out.println(f1.egale(f2));
	 System.out.println("f1="+f1);
	 f1.met1(10,20);
	 System.out.println("f1="+f1);
	 System.out.println("f2="+f2);
	 f3 = f2.met3(100,200);
	 System.out.println("f2="+f2);
	 System.out.println("f3="+f3);
	 System.out.println(f2==f3);
	 System.out.println(f2.egale(f3));

	 // point_3
	 System.out.println("---- POINT 3 ----");
   }
}