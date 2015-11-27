package dipole ;

case class Complex(real:Double, imaginary:Double) {
  private val EPSILON=1e-4 // The accuracy

  def +(other: Complex) = Complex(real + other.real, imaginary+other.imaginary)
  def inverse() = {
    //require(real > 0.0001 || imaginary > 0.00001)
    val m = real * real + imaginary * imaginary
    Complex(real/m, - imaginary / m)
  }
  override def toString() = real + " + " + imaginary + "j" 
  override def equals(other:Any):Boolean = {
    other match {
      case c: Complex => (Math.abs(real-c.real) < EPSILON) && (Math.abs(imaginary-c.imaginary)< EPSILON)
      case _ => false
    }
  }    
}



