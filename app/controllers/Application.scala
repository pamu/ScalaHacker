package controllers

import play.api.mvc.{Action, Controller}
import utils.Utils
import play.api.libs.concurrent.Execution.Implicits.defaultContext

object Application extends Controller {
  def index = Action {
    Ok(views.html.index("Scala Hack"))
  }
  def test = Action.async {
    val wsResponse = Utils.submit(
      """
        |object Solution {
        | def main(args: Array[String]): Unit = {
        |  print("hello")
        | }
        |}
      """.stripMargin, 15, """["hello"]""")
    wsResponse.map(wsResponse => {
      Ok(wsResponse.body.toString + " with status: " + wsResponse.status)
    })
  }
}