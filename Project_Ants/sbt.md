# SBT

From the [official documentation](http://www.scala-sbt.org/documentation.html)

Create a project directory 

	mkdir project_ants
	cd project_ants
	mkdir -p src/main/{scala,resources}

Add a file `build.sbt`  with the following content (empty lines are important!)

	name := "Project Ants"
	
	version := "1.0"
	
	scalaVersion := "2.11.7"
	
	libraryDependencies += "org.scala-lang" % "scala-swing" % "2.11+"

Get the demo code

	wget -O src/main/scala/DemoApp.scala https://raw.githubusercontent.com/mquinson/prog_scala/master/Project_Ants/src/DemoApp.scala
	wget -O src/main/resources/bee.png https://raw.githubusercontent.com/mquinson/prog_scala/master/Project_Ants/img/bee.png

Edit `src/main/scala/DemoApp.scala`  as follow :

	val icon:ImageIcon = new ImageIcon("img/bee.gif")

becomes

	val icon:ImageIcon = new ImageIcon(getClass.getResource("bee.png"))


Run with

	sbt run

The first launch will download the Scala library and Scala Swing.
