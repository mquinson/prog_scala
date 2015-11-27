package dipole ;

abstract class Binary(d1:Dipole, d2:Dipole) extends Dipole {
}  

case class Serie(d1:Dipole, d2:Dipole) extends Binary(d1,d2) {
    override def impedance(omega: Double):Complex = {
        return Complex(0,0)
    }
  	 override def toString() = "Serie(???)"
}

case class Parallel(d1:Dipole, d2:Dipole) extends Binary(d1,d2) {
    override def impedance(omega: Double): Complex = {
      return Complex(0,0)
    }
  	 override def toString() = "Parallel(???)"
}
