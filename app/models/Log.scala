package models

import controllers._
import play.api.Play.current
import play.api.libs.json.Json
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.modules.reactivemongo.json.BSONFormats._
import reactivemongo.bson.BSONObjectID

import scala.language.implicitConversions

import scala.collection.JavaConversions._
import org.joda.time.{Duration, LocalDateTime, DateTime, DateTimeZone}

import scala.util.Try

case class LogEntry(_id : Option[BSONObjectID] = Some(BSONObjectID.generate), product: String,
                    instance: String, username: String, actionType: String, dependencyId:Option[String], netType:Boolean, sessionId:String, deviceId:Option[String], createdOn:Long =  new DateTime().getMillis() / 1000)

/**
  *
  * LogEntry object for define data format for the ReactiveMongo Extensions.
  */
object LogEntry {
  implicit val logFormat = Json.format[LogEntry]
}