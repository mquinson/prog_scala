package dipole;

import org.scalatest._
import org.scalatest.Assertions._

class DipolesSpec extends FlatSpec with Matchers with Inside with Inspectors {
	val omega=314.16
	
	/* Simple classes */
  "Simple Resistor(1e2,0)" should "have a working impedance()" in {
    val dip = Resistor(1e2) 
    expectResult(Complex(1e2,0)) { 
      (dip.impedance(omega)) 
    }
  }  
  it should "have a working toString()" in {
    val dip = Resistor(1e2) 
    expectResult("Resistor(100.0)") {
      (dip.toString())
    }
  }
  "Simple Capacitor(42)" should "have a working impedance()" in {
    expectResult(Complex(0,-7.578789*1e-5)) { 
      (Capacitor(42).impedance(omega)) 
    }
  }
  it should "have a working toString()" in {
    expectResult("Capacitor(42.0)") { 
      (Capacitor(42).toString()) 
    }
  }
  "Simple Inductor(7e-2)" should "have a working impedance()" in {
    expectResult(Complex(0,21.9912)) { 
      (Inductor(7e-2).impedance(omega)) 
    }
  } 
  it should "have a working toString()" in {
    expectResult("Inductor(0.07)") { 
      (Inductor(7e-2).toString()) 
    }
  } 
  /* Binary classes */
  "Binary Serie Circuit" should "have a working impedance()" in {
    val dip2 = Serie(Inductor(5e-2), Resistor(1e2))
    expectResult(Complex(100,15.708)) {
      (dip2.impedance(omega))
    }
  }
  it should "have a working toString()" in {
    val dip2 = Serie(Inductor(5e-2), Resistor(1e2))
    expectResult("Serie(Inductor(0.05), Resistor(100.0))") {
      (dip2.toString())
    }    
  }
  "Binary Parallel Circuit" should "pass this first test on impedance()" in {
    val dip = Parallel( Inductor(5e-2), Capacitor(9e-4))
    expectResult(Complex(0,-4.564497387210561)) {
      (dip.impedance(omega))
    }
  }
  it should "pass this second test on impedance()" in {
    val dip = Parallel(Resistor(1e2), 
                       Parallel(Inductor(5e-2),Capacitor(9e-4)))
    expectResult(Complex(0.2079131844185524, -4.55500719534011)) {
      (dip.impedance(omega))
    }
  }
  it should "have a working toString()" in {
	  val dip = Parallel( Inductor(5e-2), Capacitor(9e-4))
    expectResult("Parallel(Inductor(0.05), Capacitor(9.0E-4))") {
      (dip.toString())
    }
  }
  /* Nary circuits */
  "Nary serie circuit" should "have a working impedance()" in {
    val dip = Nserie(Resistor(1e2) :: Capacitor(9e-4) :: Inductor(2e-1) :: Nil)
    expectResult(Complex(100,59.2952)) {
      dip.impedance(omega)
    }
  }
  it should "have a working ::()" in {
    val dip = Resistor(1e2) :: (Capacitor(9e-4) :: (Inductor(2e-1) :: Nserie(Nil)))
    expectResult(Complex(100,59.2952)) {
      dip.impedance(omega)
    }
  }
  it should "have a working toString()" in {
    val dip = Nserie(Resistor(1e2) :: Capacitor(9e-4) :: Inductor(2e-1) :: Nil)
    expectResult("Serie(Resistor(100.0), Capacitor(9.0E-4), Inductor(0.2))") {
      (dip.toString())
    }
  }
  "Nary parallel circuit" should "have a working impedance()" in {
    val dip = Nparallel(Resistor(1e2) :: Capacitor(9e-4) :: Inductor(2e-1) :: Nil)
    expectResult(Complex(0.01,0.266828)) {
      dip.impedance(omega)
    }
  }
  it should "have a working ::()" in {
    val dip = Resistor(1e2) :: (Capacitor(9e-4) :: (Inductor(2e-1) :: Nparallel(Nil)))
    expectResult(Complex(0.01,0.266828)) {
      dip.impedance(omega)
    }
  }
  it should "have a working toString()" in {
    val dip = Nparallel(Resistor(1e2) :: Capacitor(9e-4) :: Inductor(2e-1) :: Nil)
    expectResult("Parallel(Resistor(100.0), Capacitor(9.0E-4), Inductor(0.2))") {
      (dip.toString())
    }
  }
  /* Instances */
  "Stock instances" should "have a well defined dip1" in {
    expectResult("Parallel(Resistor(100.0), Serie(Inductor(5.0E-5), Resistor(12000.0)), Capacitor(9.000000000000001E-9))") {
      (Instances.dip1.toString())
    }
    //expectResult( Complex(99.17354592177011, -0.02780791066116556)) {
    expectResult( Complex(0.01008333333333319, 2.8273309166666674E-6)) {
      (Instances.dip1.impedance(omega))
    }
  }
  it should "have a well defined dip2" in {
    expectResult("Serie(Resistor(100.0), Parallel(Serie(Parallel(Serie(Resistor(1000.0), Inductor(5.0E-5)), Capacitor(0.009000000000000001), Serie(Capacitor(9.000000000000001E-4), Capacitor(1.0E-5))), Resistor(330.0)), Serie(Resistor(1000.0), Inductor(0.2))), Capacitor(1.0E-6))") {
      (Instances.dip2.toString())
    }
    expectResult( Complex(348.25678835078304, -3177.629770728981)) {
      (Instances.dip2.impedance(omega))
    }
    
  }
}
