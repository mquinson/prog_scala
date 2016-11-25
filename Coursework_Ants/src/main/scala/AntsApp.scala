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
import javax.swing.{ ImageIcon, Timer }
import javax.swing.UIManager

/** This class should be the ancestor of every images on screen (bees, ants, etc) */
abstract class Sprite(val image_file : String, var x : Int, var y : Int) {
	/* Load the image that move on screen */
	
    //private val path = Option(getClass.getResource("/resources/"+image_file)) // Use this line if you prefer eclipse
	private val path = Option(getClass.getResource(image_file))  // This line is intended for sbt users
    val icon = path match {
      case None => 
    	  println("Cannot find the file "+image_file+". Make sure it's in the classpath.")
    	  UIManager.getLookAndFeelDefaults().get("html.missingImage").asInstanceOf[ImageIcon]
      case Some(url) => new ImageIcon(url)
    }
    val im = icon.getImage()

	def update() = {} // Override this to get your sprite doing something
}

/** Demo of Sprite implementation */
class Bee extends Sprite("bee.png",200,200) {
	var counter : Int = 0
	var x_speed : Int = 2
	
	// Move on every frame and switch direction every second
	override def update() = {
		x += x_speed;
		counter+=1;
		if (counter % 50 == 0) {
			println("One second")
			x_speed *= -1
		}
	}	
}

/** Box objects can be clicked. Somehow similar to buttons */
abstract class Box(var x : Int, var y : Int){
	val size : Int = 100

	val boxPath = new geom.GeneralPath
	boxPath.moveTo(x,y)
	boxPath.lineTo(x+size,y)
	boxPath.lineTo(x+size,y+size)
	boxPath.lineTo(x,y+size)
	boxPath.lineTo(x,y)

	def onClick() // Override this method to do something when this box gets clicked 

	def isInside(pt : Point): Boolean = 
		(pt.x <= x+size && pt.x >= x && pt.y <= y + size && pt.y >= y)

}

/** Demo of Box implementation */	
class SimpleBox extends Box(200,200) {
  def onClick() = println("SimpleBox was clicked")
}

/** This is the main application */
object AntsApp extends Engine {
	val WIDTH = 1200
	val HEIGHT = 800
	
	init_screen(WIDTH, HEIGHT)
	add_object(new Bee)
	
	sprites = new Bee :: sprites
    	
	boxes = new SimpleBox :: boxes
}
class Engine extends SimpleSwingApplication {

	// lists that contain the sprites and the boxes for the display
	var sprites : List[Sprite] = Nil
	var boxes : List[Box] = Nil

	def add_object(sp:Sprite) = { sprites = sp::sprites }
	// Method that calls sprite.update for all sprites displayed
	def update_sprites() = sprites.map(_.update())
	
	// Core of the game turns	
	def gameTurn() {
		println("New game turn")
	}

	def init_screen(width: Int, height: Int) = 
		ui.preferredSize = new Dimension(width, height)
	// Display
	lazy val ui = new Panel {
		preferredSize = new Dimension(640, 480)
		background = Color.white
		
    	focusable = true
    	listenTo(mouse.clicks, mouse.moves, keys) // Capture mouse and keyboard

		// In case of action from the user ...
		reactions += {
			case e: MousePressed => 
				for (box<-boxes){
					if (box.isInside(e.point)){
						box.onClick()
					}
				}
			case e: MouseDragged =>
			case e: MouseReleased =>
			case _: FocusLost => repaint()
		}

		
		// Display method
		override def paintComponent(g: Graphics2D) = {
			super.paintComponent(g)
			
			// draw all boxes
			for (box <- boxes)
				g.draw(box.boxPath)	

			// Draw all sprites
			for (sprite <- sprites)
				g.drawImage(sprite.im, sprite.x, sprite.y, peer)
		}
	}
	
	// Animation timer: calls state.update() and ui.repaint() 50 times per second
	class MyTimer extends ActionListener {
		/* Configuration */
		val fpsTarget = 50 // Desired amount of frames per second
		var delay = 1000 / fpsTarget
		val delayBetweenTurns = 3
		var framesBetweenTurns = delayBetweenTurns * fpsTarget

		// Counter to run the turns every delayBetweenTurns seconds
		var framesSinceLastTurn = 0

		/* The swing timer */
		val timer = new Timer(delay, this)
		timer.setCoalesce(true) // Please restart by yourself
		timer.start()           // Let's go

		/* react to the timer events */
		def actionPerformed(e: ActionEvent): Unit = {
			framesSinceLastTurn+=1
			if (framesSinceLastTurn == framesBetweenTurns){
				framesSinceLastTurn = 0
				gameTurn()
			}
			update_sprites()
			ui.repaint() // Ask for an eventual repaint
		}
	}
	val t = new MyTimer()

	def top = new MainFrame {
		title = "Ants vs. Bees"
		contents = ui
	}
}
