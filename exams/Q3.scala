class Plant {
  def watering() = println("Watering a plant")
}
class Cactus extends Plant {
  override def watering() = println("Watering a cactus")
}

val plant:Plant = new Cactus()
val cactus:Plant =  new Plant()
plant.watering()
cactus.watering()
