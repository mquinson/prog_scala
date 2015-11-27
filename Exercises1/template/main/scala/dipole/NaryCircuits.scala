package dipole ;

abstract class Nary(list: List[Dipole]) extends Dipole {
  def ::(other: Dipole): Nary
}

case class Nparallel(list: List[Dipole]) extends Nary(list) {
      
  override def impedance(omega: Double) = {
    Complex(0,0);
  }
    
  override def toString():String = {
  	 "Parallel(??)"
  }
    
  def ::(other: Dipole) = {
    this
  }

}

case class Nserie(list: List[Dipole]) extends Nary(list: List[Dipole]) {
  override def impedance(omega: Double): Complex = {
    return Complex(0,0)
  }
  override def toString():String = {
  	 "Serie(??)"
  }
  override def ::(head: Dipole):Nserie = { 
    this 
  }
}


