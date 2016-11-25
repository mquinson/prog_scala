// This is the template of code that you should fill.

// You are free to reorganize your code as you want. 
// In particular, splitting this file in several subfiles sounds like a good idea

// Compile this file with: scalac -cp ../../../scala-swing.jar:. AntsApp.scala
// Then execute with: scala -cp ../../../scala-swing.jar:. AntsApp
// (from the src/main/scala directory)

import scala.swing._
import scala.swing.{ SimpleSwingApplication, MainFrame, Panel }
import scala.swing.event._
import java.awt.event.{ ActionEvent, ActionListener }
import java.awt.{ Color, Graphics2D, Point, geom, MouseInfo }
import javax.swing.{ ImageIcon, Timer }
import javax.swing.UIManager


abstract class Sprite(val image_file : String, var x : Int, var y : Int) {
	

	/* Load the images that move on screen. Search in two locations so that it works with sbt and eclipse */
    val path = Option(getClass.getResource("/resources/"+image_file))
    val path2 = if (path != None) path else Option(getClass.getResource(image_file)) 
    val icon = path2 match {
      case Some(url) => new ImageIcon(url)
      case None      => {
    	  println("Cannot find the file "+image_file+". Make sure it's in the classpath.")
    	  UIManager.getLookAndFeelDefaults().get("html.missingImage").asInstanceOf[ImageIcon]        
      }
    }
    val im = icon.getImage( )

	// Method to override to have updates on every frame
	def update() = {}
}

abstract class Box(var x : Int, var y : Int){
	val size : Int = 100

	val boxPath = new geom.GeneralPath
	boxPath.moveTo(x,y)
	boxPath.lineTo(x+size,y)
	boxPath.lineTo(x+size,y+size)
	boxPath.lineTo(x,y+size)
	boxPath.lineTo(x,y)


	// To override for action on click
	def onClick() = {}

	def isInside(pt : Point): Boolean = {
		(pt.x <= x+size && pt.x >= x && pt.y <= y + size && pt.y >= y)
	}

}



class Bee extends Sprite("bee.png",200,200) {
	//Example of bee

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
	
class SimpleBox extends Box(200,200) {
	// Simple implementation of Box at the coordinates 200 200
}



object AntsApp extends SimpleSwingApplication {

	/** 
	Called when there is a click on a box b
		parameter b : box that has been clicked on
	**/	
	def clickOnBox(b : Box) {
		
		println("Inside box")
	}



	// lists that contain the sprites and the boxes for the display
	var sprites : List[Sprite] = Nil
	var boxes : List[Box] = Nil


	// Temporary examples
	sprites = new Bee :: sprites
	boxes = new SimpleBox :: boxes

	// Method that calls sprite.update for all sprites displayed
	def update_sprites() {
		for (sprite <- sprites){
			sprite.update()
		}
	}
	
	// Core of the game turns	
	def gameTurn() {
		println("Game turn")
	}

	// Display
	lazy val ui = new Panel {
		preferredSize = new Dimension(600, 400)
		background = Color.white

		
    	focusable = true
    	listenTo(mouse.clicks, mouse.moves, keys) // Capture mouse and keyboard

		// In case of action from the user ...
		reactions += {
			case e: MousePressed => 
				for (box<-boxes){
					if (box.isInside(e.point)){
						box.onClick()
						clickOnBox(box)
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
			for (box <- boxes){
				g.draw(box.boxPath)	
			}

			// Draw all sprites
			for (sprite <- sprites){
				g.drawImage(sprite.im,sprite.x,sprite.y,peer)
			}
			

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
			ui.repaint() // Tell Scala that the image should be redrawn
		}
	}
	val t = new MyTimer()

	def top = new MainFrame {
		title = "Game"
		contents = ui
	}
}
