// This is the template of code that you should fill.

// You are free to reorganize your code as you want. 
// In particular, splitting this file into several files is a good idea

// Compile: scalac -cp ../../../scala-swing.jar:. AntsApp.scala
// Execute: scala -cp ../../../scala-swing.jar:. AntsApp
// (from the src/main/scala directory)

import scala.swing._
import scala.swing.{ SimpleSwingApplication, MainFrame, Panel }
import scala.swing.event._
import java.awt.event.{ ActionEvent, ActionListener }
import java.awt.{ Color, Graphics2D, Point, geom, MouseInfo }
import javax.swing.{ ImageIcon, Timer, AbstractAction }
import javax.swing.UIManager

abstract class Sprite {
	var x = 0
	var y = 0
	var delta_x = 1
	var delta_y = 0
	var age = 0
	
	/** Called 50 times per second. 
	 * Override this to get your object doing something useful */
	def update() = {
		x += delta_x
		y += delta_y
		age += 1
	}
	def onClick() = {} // Called when you're clicked
	// Returns true if the object gets out of bound. The engine removes such objects
	def isoob(maxX:Int, maxY:Int) = { (x <0 && x >= maxX) || (y < 0 && y >= maxY)}
	
	def paint(g: Graphics2D, panel: javax.swing.JPanel) // Draw yourself
	def isInside(pt : Point): Boolean = false // Detect whether the (clicked) point is touching you
	override def finalize() = println("Bye")
}

/** This class should be the ancestor of every images on screen (bees, ants, etc) */
abstract class BitmapSprite(val image_file : String) extends Sprite {

    //private val path = Option(getClass.getResource("/resources/"+image_file)) // Use this line if you prefer eclipse
	private val path = Option(getClass.getResource(image_file))  // This line is intended for sbt users
	
    val icon = path match {
      case None => 
    	  println("Cannot find the file "+image_file+". Make sure it's in the classpath.")
    	  UIManager.getLookAndFeelDefaults().get("html.missingImage").asInstanceOf[ImageIcon]
      case Some(url) => new ImageIcon(url)
    }
    val image = icon.getImage()

    override def paint(g: Graphics2D, panel: javax.swing.JPanel) = { g.drawImage(image, x, y, panel) }
}

/** Demo of BitmapSprite implementation */
class Bee extends BitmapSprite("bee.png") {
	x = 200
	y = 200
	val size = 50
	override def update() = {
		if (age % 50 == 0) {
			println("Changing direction. Age: "+age)
			delta_x = -delta_x
		}
		super.update()
	}	
	override def isInside(pt : Point) = 
		(pt.x <= x+size && pt.x >= x && pt.y <= y + size && pt.y >= y)
	override def onClick() = {
		println("You got the Bee!")
		AntsApp.del_object(this)
	}
}

/** Shape objects can are defined by a graphics path, not a png */
abstract class Shape extends Sprite {
	val shape = new geom.GeneralPath
	shape.moveTo(0,0)
		
	override def paint(g: Graphics2D, panel: javax.swing.JPanel) = {
		g.translate(x,y)
		g.draw(shape)
		g.translate(-x,-y)
	}
}

/** Demo of Shape implementation */	
class SimpleBox extends Shape {
	x = 200
	y = 200
	delta_x = 0
	val size = 100

	shape.lineTo(size,0)
	shape.lineTo(size,size)
	shape.lineTo(0,   size)
	shape.lineTo(0,   0)

	override def isInside(pt : Point) = 
		(pt.x <= x+size && pt.x >= x && pt.y <= y + size && pt.y >= y)

	override def onClick() = {
		println("Box was clicked. Good bye.")
		AntsApp.del_object(this)
	}
}

/** This is the main application */
object AntsApp extends Engine {
	val WIDTH = 1200
	val HEIGHT = 800
	
	init_screen(WIDTH, HEIGHT)
	add_object(new Bee)
	add_object(new SimpleBox)
}
class Engine extends SimpleSwingApplication {
	val fpsTarget = 50 // Desired amount of frames per second
	val appTitle = "Ants vs. Bees" // Windows title
	
	// lists that contain the game sprites. Don't change it directly.
	var sprites : List[Sprite] = Nil

	def add_object(sp:Sprite) = { sprites = sp::sprites }
	def del_object(sp:Sprite) = { 
		sprites = sprites.filter( _ != sp ) 
		if (sprites isEmpty) {
			println("No sprite left. Exit the game.")
			sys.exit()
		}
	}
	def init_screen(width: Int, height: Int) = ui.preferredSize = new Dimension(width, height)
	
	// Core of the game turns	
	def gameTurn() {
		println("New game turn")
	}

	// Display Panel
	lazy val ui = new Panel {
		background = Color.white
    	focusable = true
    	listenTo(mouse.clicks, mouse.moves, keys) // Capture mouse and keyboard

		// In case of action from the user ...
		reactions += {
			case e: MousePressed => 
				sprites.filter(_.isInside(e.point)).map(_.onClick() )
			case e: MouseDragged =>
			case e: MouseReleased =>
			case _: FocusLost => repaint()
		}
		
		// Repaint the component when asked
		override def paintComponent(g: Graphics2D) = {
			// clear board
			g.setPaint(Color.white);
			g.fill(new geom.Rectangle2D.Double(0, 0, size.width, size.height));
		    g.setPaint(Color.black);
			// Draw all sprites
			sprites.map( sprite => sprite.paint(g, peer) ) 
		}
		
		// Redraw things at 80 fps, executed in the GUI thread for fluid animations
		val timer = new javax.swing.Timer(1000/80, new AbstractAction() {
			def actionPerformed(e: java.awt.event.ActionEvent) { repaint }
		}).start
	}

	
	private[this] val timer = new java.util.Timer
    timer.scheduleAtFixedRate(new java.util.TimerTask {
		def run { 
			sprites.map(_.update())
			sprites.filter(_.isoob(ui.size.width, ui.size.height) )
			       .map( del_object(_) )
		}
    }, 0, 1000/fpsTarget) 
  
	def top = new MainFrame {
		title = appTitle
		contents = ui	
	}
}
