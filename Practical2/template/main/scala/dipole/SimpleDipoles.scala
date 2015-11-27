package dipole;

case class Resistor(resistance: Double) extends Dipole {
    override def impedance(omega: Double):Complex = {
      Complex(0,0)
    }
     override def toString() = "some resistor"
} 

case class Capacitor(capacitance: Double) extends Dipole {
    override def impedance(omega: Double):Complex = {
      Complex(0,0)
    }
     override def toString() = "some capacitor"
}

case class Inductor(inductance: Double) extends Dipole {
    override def impedance(omega: Double):Complex = {
        Complex(0,0)
    }
    
     override def toString() = "some inductor"
}
