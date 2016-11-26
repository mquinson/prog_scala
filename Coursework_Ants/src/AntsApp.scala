// This is the template of code that you should fill.

// You are free to reorganize your code as you want. 
// In particular, splitting this file into several files is a good idea.

// From the root directory:
// Compile: scalac -cp scala-swing.jar:. src/AntsApp.scala
// Execute: scala -cp scala-swing.jar:.:gfx AntsApp

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
	var dx = 0
	var dy = 0
	var age = 0
    var size = new Dimension(1,1) // By default, sprites are only one point
	
	/** Called 50 times per second (on each tick). Override this to put your game logic. */
	def onTick() = {
		x += dx
		y += dy
		age += 1
	}
    /** Called when the object is clicked */
	def onClick() = {} 
    
	/** Returns true if the object gets out of bound. The engine removes objects that do */
	def isOob(maxX:Int, maxY:Int) = 
        x < 0 || x+size.width >= maxX || y < 0 || y+size.height >= maxY
	
    /** Ask the object to draw itself on the panel */
	def paint(g: Graphics2D, panel: javax.swing.JPanel)
    
    /** Detect whether the (clicked) point is touching the object */
	def isInside(pt : Point): Boolean = 
        (pt.x <= x+size.width && pt.x >= x && pt.y <= y + size.height && pt.y >= y)
}

/** This class should be the ancestor of every images on screen (bees, ants, etc) */
abstract class BitmapSprite(imageFile:String) extends Sprite {

    //private[this] val path = Option(getClass.getResource("/gfx/"+imageFile)) // Use this line if you prefer eclipse
	private[this] val path = Option(getClass.getResource(imageFile))  // This line is intended for sbt users
	
    private[this] val icon = path match {
      case None => 
    	  println("Cannot find the file "+imageFile+". Make sure it's in the classpath.")
    	  UIManager.getLookAndFeelDefaults().get("html.missingImage").asInstanceOf[ImageIcon]
      case Some(url) => new ImageIcon(url)
    }
    val image = icon.getImage()
    // My size is the one of the image
    size = new Dimension(icon.getIconWidth,icon.getIconHeight)

    override def paint(g: Graphics2D, panel: javax.swing.JPanel) = 
		g.drawImage(image, x, y, panel) 
}

/** Box paints itself directly on the Graphics2D */	
class Box extends Sprite {
	// position (x,y) and speed (dx, dy) are defined in Sprite: don't redefine them here but give them a value
	x = 200 
	y = 200
	dx = -1
	dy = 1
    size = new Dimension(100,100)

	// See https://docs.oracle.com/javase/tutorial/2d/TOC.html to learn about Java 2D graphics
	override def paint(g: Graphics2D, panel: javax.swing.JPanel) = {
		g.setPaint(Color.red)
		g.fill(new geom.Rectangle2D.Double(x, y, size.width, size.height))
	}

	// Returns true if the object gets out of bound. The engine removes objects that do
	override def isOob(sizeX:Int, sizeY:Int) = 
		x<0 || x+size.width>sizeX || y<0 || y+size.height>sizeY

	// How should this sprite react to mouse clicks?
	override def onClick() = {
		println("Box was clicked. Good bye.")
		AntsApp.delObject(this)
	}
}

/** Bee is a simple Sprite defined from a bitmap file */
class Bee extends BitmapSprite("bee.png") {
	// Bees spawn to random locations
	x = AntsApp.rand.nextInt(AntsApp.size.width-size.width)
	y = AntsApp.rand.nextInt(AntsApp.size.height-size.height)
	
	// Called on each tick (50 times/second). Contains the game logic
	override def onTick() = {
		// age is automatically updated by the engine (counted in ticks)
		if (age % 50 == 0) {
			println("Changing direction because age="+age)
			dx = AntsApp.rand.nextInt(3)-1 // Take a number in [O, 3) (which is 0, 1 or 2) and then substract 1
			dy = AntsApp.rand.nextInt(3)-1 // So it will be one of -1, 0, 1.
		}
		super.onTick() // The bee won't move if you remove this
	}	
		
	// How to react to mouse clicks
	override def onClick() = {
		println("You got the Bee!")
		AntsApp.delObject(this)
	}
}

/** This is the main application of our little example */
object AntsApp extends Engine {
	override val appTitle = "Ants vs. Bees" // Windows title

	// The bees need such a random number generator
	val rand = new scala.util.Random()
	
	// Setup and populate the game at the beginning
	setSize(1200, 800)
	addObject(new Box)
	
	// Called 50 times per second (on each tick). Contains the game logic
	override def onTick() {
		if (ticks % 60 == 0) 
			addObject(new Bee)
	}
}
abstract class Engine extends SimpleSwingApplication {
	var ticks = 0
	
	/** List of all known sprites. Iterate over them, but don't change them directly. */
	var sprites : List[Sprite] = Nil

	/*
	 * Public functions that you may want to use
	 */

    /** Registers a new Sprite to the engine */
	def addObject(sp:Sprite) = {
		sprites = sp::sprites 
	}
    /** Unregisters a Sprite from the engine. The game stops when all sprites are gone */
	def delObject(sp:Sprite) = { 
		sprites = sprites.filter( _ != sp ) 
		if (sprites isEmpty) {
			println("No sprite left. Exit the game.")
			sys.exit()
		}
	}
    /** Returns the current size of the window */
	def size() = if (ui.size.width > 0) ui.size else new Dimension(640,800)
    /** Sets the preferred size of the window */
 	def setSize(width: Int, height: Int) = ui.preferredSize = new Dimension(width, height)
	
    /** Put your global game logic in this function */
	def onTick() 

	/*
	 * Implementation part. You should not have to change the following
	 */
	def internalTickHandler() = { // Separated to ensure that not calling super.onTick in onTick does not break everything
		ticks += 1
		onTick()
		sprites.map(_.onTick())
		
		// Delete oob objects
		sprites.filter(_.isOob(size.width, size.height) ).map( delObject(_) )
	}

	val fpsTarget:Int = 50 // Desired amount of ticks and frames per second. Don't change it

	// Timer in charge of the game update
	private[this] val timer = new java.util.Timer
    timer.scheduleAtFixedRate(new java.util.TimerTask {
		def run() = internalTickHandler()
    }, 0, 1000/fpsTarget) 
  
	// Display Panel
	lazy val ui = new Panel {
		background = Color.white
		preferredSize = new Dimension(800,640)
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
		
		// Redraw things at 50 fps, executed in the GUI thread for fluid animations
		val timer = new javax.swing.Timer(1000/fpsTarget, new AbstractAction() {
			def actionPerformed(e: java.awt.event.ActionEvent) { repaint }
		}).start
	}

  	val appTitle:String = "Some name" // Windows title, to be overriden
	def top = new MainFrame {
		title = appTitle
		contents = ui	
	}
}
