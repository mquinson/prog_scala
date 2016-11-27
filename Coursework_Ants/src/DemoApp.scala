// This is the template of code that you should fill.

// You are free to reorganize your code as you want. 
// In particular, splitting and renaming this file is a good idea.

// From the root directory:
// Compile: scalac -cp scala-swing.jar:. src/DemoApp.scala src/Engine.scala
// Execute: scala -cp scala-swing.jar:.:gfx DemoApp

import engine._ // Import all of the engine package
import scala.swing.event.Key._ // Names of keystrokes (for the keyboard handler)
import java.awt.{ Color, Dimension, Graphics2D, geom } // To draw on the sprites

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
		DemoApp.delObject(this)
	}
}

/** Bee is a simple Sprite defined from a bitmap file */
class Bee extends BitmapSprite("bee.png") {
	// Bees spawn to random locations
	x = DemoApp.rand.nextInt(DemoApp.size.width-size.width)
	y = DemoApp.rand.nextInt(DemoApp.size.height-size.height)
	
	// Called on each tick (50 times/second). Contains the game logic
	override def onTick() = {
		// age is automatically updated by the engine (counted in ticks)
		if (age % 50 == 0) {
			println("Changing direction because age="+age)
			dx = DemoApp.rand.nextInt(3)-1 // Take a number in [O, 3) (which is 0, 1 or 2) and then substract 1
			dy = DemoApp.rand.nextInt(3)-1 // So it will be one of -1, 0, 1.
		}
		super.onTick() // The bee won't move if you remove this
	}	
		
	// How to react to mouse clicks
	override def onClick() = {
		println("You got the Bee!")
		DemoApp.delObject(this) // remove this object from the game
	}
}

/** This sprite is used in the onMouseMove method of our application */
class MouseTracker extends Sprite {
    size = new Dimension(25,25)

	override def paint(g: Graphics2D, panel: javax.swing.JPanel) = {
		g.setPaint(Color.magenta)
		g.fill(new geom.Rectangle2D.Double(x, y, size.width, size.height))
	}
    // Don't let the engine destroy this object
    override def isOob(sizeX:Int, sizeY:Int) = false
}


/** This is the main application of our little example */
object DemoApp extends Engine {
	override val appTitle = "Ants vs. SomeBees" // Windows title

	// The bees need such a random number generator
	val rand = new scala.util.Random()
	
	// Setup and populate the game at the beginning
	setSize(1200, 800)
    addObject(new Box)
    val mouseTracker = new MouseTracker
    addObject(mouseTracker)
    for (i <- 1 to 10)
        addObject(new Bee)
	
	// Called 50 times per second (on each tick). Contains the game logic
	override def onTick() {
		if (ticks % 60 == 0) 
			addObject(new Bee)
	}
    
    // How to react to keystrokes
    override def onKeyPress(keyCode: Value) = keyCode match {
        // Keys names are here https://github.com/scala/scala-swing/blob/2.0.x/src/main/scala/scala/swing/event/Key.scala
        case Left  => println("Left pressed")
        case Right => println("Right pressed")
        case Up    => println("Up pressed")
        case Down  => println("Down pressed")
        case _ =>
    }
    
    // How to react to mouse moves
    override def onMouseMove(x:Int, y:Int) = {
        mouseTracker.x = x
        mouseTracker.y = y
        println("The mouse is at "+x+","+y)
    }
}
