package controllers

import play.api.libs.json._
import play.api.mvc.{Action, Controller}
import utils.Utils
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.Future

import play.api.libs.functional.syntax._

object Application extends Controller {
  def index = Action {
    Ok(views.html.index("Scala Hack"))
  }

  case class ScalaHacker(source: String, lang: Int, testcases: List[String])
  implicit val reads: Reads[ScalaHacker] = (
    (JsPath \ "source").read[String] and
      (JsPath \ "lang").read[Int] and
      (JsPath \ "testcases").read[List[String]]
    )(ScalaHacker.apply _)

  def hackerrank() = Action.async(parse.json) { implicit request =>
    request.body.validate[ScalaHacker] match {
      case success: JsSuccess[ScalaHacker] => {
        val value = success.get
        Utils.submit(value.source, value.lang, value.testcases)
          .map(wsResponse => Ok(Json.obj("success" -> wsResponse.body)))
      }
      case error: JsError => {
        Future(Ok(Json.obj("failure" -> "Bad Json Format")))
      }
    }
  }
  def test = Action.async {
    val wsResponse = Utils.submit(
      """
        |object Solution {
        | def main(args: Array[String]): Unit = {
        |  print("hello")
        | }
        |}
      """.stripMargin, 15, List("hello"))
    wsResponse.map(wsResponse => {
      Ok(wsResponse.body.toString + " with status: " + wsResponse.status)
    })
  }
}