package controllers

import javax.inject.{Inject, Singleton}

import models.LogEntry
import play.Logger
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.{JsError, Json}
import play.api.mvc._
import reactivemongo.bson.BSONObjectID
import models.LogFormats._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.api.Cursor

@Singleton
class Application @Inject() (val reactiveMongoApi: ReactiveMongoApi)
  extends Controller with MongoController with ReactiveMongoComponents {

  //def logsFuture: Future[JSONCollection] = database.map(_.collection[JSONCollection]("sweta"))

  def collection: JSONCollection = db.collection[JSONCollection]("logentry")

  def createFromJson = Action.async(parse.json) { request =>
    request.body.validate[LogEntry].map { user =>
      collection.insert(user).map { lastError =>
        Logger.debug(s"Successfully inserted with LastError: $lastError")
        Created
      }
    }.getOrElse(Future.successful(BadRequest("invalid json")))
  }

  import javax.inject._

  import models.LogEntry
  import play.api.Logger
  import play.api.libs.json._
  import play.api.mvc._
  import play.modules.reactivemongo._
  import reactivemongo.api.ReadPreference
  import reactivemongo.play.json._
  import reactivemongo.play.json.collection._

  import scala.concurrent.{ExecutionContext, Future}
  def findByProduct(product: String) = Action.async {
    // let's do our query
    val cursor: Cursor[LogEntry] = collection.
      // find all people with name `name`
      find(Json.obj("product" -> product)).
      // sort them by creation date
      sort(Json.obj("created" -> -1)).
      // perform the query and get a cursor of JsObject
      cursor[LogEntry]

    // gather all the JsObjects in a list
    val futureUsersList: Future[List[LogEntry]] = cursor.collect[List]()

    // everything's ok! Let's reply with the array
    futureUsersList.map { p =>
      Ok(p.toString)
    }
  }
}


//curl -XPOST -H "Content-Type:application/json" -d '{"_id":"s", "product":"sweta","instance":"sweta","username":"sweta","actionType":"test40", "dependencyId":"test40","netType":true,"sessionId":"sessa","deviceId":"device","createdOn":122}' localhost:9000/logs
