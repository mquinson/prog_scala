public class TestPoint {

   public static void main(String[] args) {
     (new TestPoint()).run();
   }

   public void run() {
     Point p = new Point();
	 double expected = 0.;
	 double value = p.getX();
     check("Test origine.getX()", value == expected, "expected="+expected+" value="+value);   

	 value = p.getY();
     check("Test origine.getY()", value == expected, "expected="+expected+" value="+value);

     p = new Point(3.,2.);

	 expected = 3.;
	 value = p.getX();
     check("Test new(3.,2.).getX()", value == expected, "expected="+expected+" value="+value);   
   
     expected = 2.;
	 value = p.getY();
     check("Test new(3.,2.).getY()", value == expected, "expected="+expected+" value="+value);

     Point p2 = new Point(p);

	 expected = 3.;
	 value = p2.getX();
     check("Test copy(3.,2.).getX()", value == expected, "expected="+expected+" value="+value);   
   
     expected = 2.;
	 value = p2.getY();
     check("Test copy(3.,2.).getY()", value == expected, "expected="+expected+" value="+value);

     p.setX(4.);

	 expected = 4.;
	 value = p.getX();
     check("Test setX(4.).getX()", value == expected, "expected="+expected+" value="+value);   

     p.setY(7.);

	 expected = 7.;
	 value = p.getY();
     check("Test setY(7.).getY()", value == expected, "expected="+expected+" value="+value);   

     p = new Point(2.,3.);
     p.translater(4.,7.);
     double exp1 = 2.+4.;
     double exp2 = 3.+7.;
     double v1 = p.getX();
     double v2 = p.getY();

     check("Test (2.,3.).translater(4.,7.)", v1 == exp1, "exp1="+exp1+" v1="+v1);   
     check("Test (2.,3.).translater(4.,7.)", v2 == exp2, "exp2="+exp2+" v2="+v2);   
    

   }


   public void check(String message, boolean condition, String debug) {
     System.out.print(message+"\t");
	 if (! condition) {
        System.out.println("[failed] \n"+debug);
     } else {
		System.out.println("[ok]");
	 }
   }



}