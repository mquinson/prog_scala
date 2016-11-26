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

/** Class of objects moving (or not) on the game panel */
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
	def isoob(maxX:Int, maxY:Int) = { (x < 0 || x >= maxX) || (y < 0 || y >= maxY)}
	
	def paint(g: Graphics2D, panel: javax.swing.JPanel) // Draw yourself
	def isInside(pt : Point): Boolean = false // Detect whether the (clicked) point is touching you
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
	val size = 50
	
	if (AntsApp.size.width >0) {
		x = AntsApp.rand.nextInt(AntsApp.size.width-size)
		y = AntsApp.rand.nextInt(AntsApp.size.height-size)
	}
	override def update() = {
		if (age % 50 == 0) {
			println("Changing direction. Age: "+age)
			delta_x = AntsApp.rand.nextInt(3)-1
			delta_y = AntsApp.rand.nextInt(3)-1
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

/** Box paints itself directly on the Graphics2D */	
class Box extends Sprite {
	x = 200
	y = 200
	delta_y = -1
	val size = 100

	override def paint(g: Graphics2D, panel: javax.swing.JPanel) = {
		g.setPaint(Color.red)
		g.fill(new geom.Rectangle2D.Double(x, y, size, size))
	}

	override def isInside(pt : Point) = 
		(pt.x <= x+size && pt.x >= x && pt.y <= y + size && pt.y >= y)

	override def onClick() = {
		println("Box was clicked. Good bye.")
		AntsApp.del_object(this)
	}
}

/** This is the main application */
object AntsApp extends Engine {
	override val appTitle = "Ants vs. Bees" // Windows title

	val rand = new scala.util.Random()
	
	override def update() {
		if (ticks % 60 == 0) 
			add_object(new Bee)
		super.update()
	}
	
	setSize(1200, 800)
	add_object(new Bee)
	add_object(new Box)
}
abstract class Engine extends SimpleSwingApplication {
	var ticks = 0
	
	// All known sprites. Iterate over them, but don't change them directly.
	var sprites : List[Sprite] = Nil

	/*
	 * Public functions that you may want to use
	 */

	def add_object(sp:Sprite) = { sprites = sp::sprites }
	def del_object(sp:Sprite) = { 
		sprites = sprites.filter( _ != sp ) 
		if (sprites isEmpty) {
			println("No sprite left. Exit the game.")
			sys.exit()
		}
	}
	def size() = ui.size
	def setSize(width: Int, height: Int) = ui.preferredSize = new Dimension(width, height)
	
	def update() = { // Game update function. Called on each tick.
		ticks += 1
		sprites.map(_.update())
		
		// Delete oob objects
		val (maxX, maxY) = (ui.size.width, ui.size.height)
		if (maxX>0) // Don't get mad if called before the UI creation
			sprites.filter(_.isoob(maxX, maxY) ).map( del_object(_) )
	}

	/*
	 * Implementation part. You should not have to change the following
	 */

	val fpsTarget:Int = 50 // Desired amount of frames per second. Overridable.

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
		val timer = new javax.swing.Timer(1000/fpsTarget, new AbstractAction() {
			def actionPerformed(e: java.awt.event.ActionEvent) { repaint }
		}).start
	}

	// Timer in charge of the game update
	private[this] val timer = new java.util.Timer
    timer.scheduleAtFixedRate(new java.util.TimerTask {
		def run() = update()
    }, 0, 1000/fpsTarget) 
  
  	val appTitle:String = "Some name" // Windows title, to be overriden
	def top = new MainFrame {
		title = appTitle
		contents = ui	
	}
}
