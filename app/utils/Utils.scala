package utils

import constants.Constants
import models.Submission
import play.api.libs.ws.{WSResponse, WS}
import play.api.Play.current

import scala.concurrent.Future

/**
 * Created by pnagarjuna on 13/05/15.
 */
object Utils {
  def submit(source: String, lang: Int, testcases: List[String]): Future[WSResponse] = {
    val submission = Submission(source, lang, testcases, Constants.API_KEY, true, "", "json")
    implicit class Utils(map: Map[String, String]) {
      def convert: List[String] =
        map.map(pair => pair._1 + "=" + pair._2).toList
    }

    val holder = WS.url(Constants.SubmissionURL)
      .withHeaders("Content-Type" -> "application/x-www-form-urlencoded")
      .withHeaders("Accept" -> "application/json")
      .post(Map[String, String](
      ("source" -> submission.source),
      ("lang" -> submission.lang.toString),
      ("testcases" -> submission.testcases.mkString("""["""", """"],["""", """"]""")),
      ("api_key" -> submission.api_key),
      ("wait" -> submission.waitParam.toString),
      ("format" -> submission.format)
    ).convert.mkString("", "&", ""))
    holder
  }
}
