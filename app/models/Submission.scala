package models

/**
 * Created by pnagarjuna on 13/05/15.
 */
 case class Submission(source: String,
                       lang: Int,
                       testcases: List[String],
                       api_key: String,
                       waitParam: Boolean,
                       callback_url: String,
                       format: String )
