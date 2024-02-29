package controllers

import javax.inject.*
import play.api.*
import play.api.mvc.*

@Singleton
class PlantController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def database() = Action {
    Ok(views.html.plantdatabase())
  }

}
