public class Point {
    private double x;
    private double y;

    public Point() {
      this.x = 0.;
      this.y = 0.;
    }

    public Point(double someX, double someY) {
      this.x = someX;
      this.y = someY;
    }

    public Point(Point p) {
	  this.x = p.x;
	  this.y = p.y;
    }

	public double getX() {
	  return this.x;
	}
	
	public double getY() {
	  return this.y;
	}
	
	public void setX(double newX) {
	  this.x = newX;
	}
	
	public void setY(double newY) {
	  this.y = newY;
	}
	
	public void translater(double dx, double dy) {
	  this.x += dx;
	  this.y += dy;
	}	
}