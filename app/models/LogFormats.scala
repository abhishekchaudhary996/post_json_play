package models

import play.api.libs.functional.syntax._
import play.api.libs.json.{Reads, JsPath, Writes}

object LogFormats {
  implicit def logWrites: Writes[LogEntry] = (
    (JsPath \ "id").write[String] and
      (JsPath \ "product").write[String] and
      (JsPath \ "instance").write[String] and
      (JsPath \ "username").write[String] and
      (JsPath \ "actionType").write[String] and
      (JsPath \ "dependencyId").writeNullable[String] and
      (JsPath \ "netType").write[Boolean] and
      (JsPath \ "sessionId").write[String] and
      (JsPath \ "deviceId").writeNullable[String] and
      (JsPath \ "createdOn").write[Long]
    )(log => (log._id.toString, log.product,
    log.instance, log.username, log.actionType, log.dependencyId, log.netType, log.sessionId, log.deviceId, log.createdOn))

  implicit def logListWrites: Writes[List[LogEntry]] = Writes.list(logWrites)

  implicit def logReads: Reads[LogEntry] = (
    (JsPath \ "product").read[String] and
      (JsPath \ "instance").read[String] and
      (JsPath \ "username").read[String] and
      (JsPath \ "actionType").read[String] and
      (JsPath \ "dependencyId").readNullable[String] and
      (JsPath \ "netType").read[Boolean] and
      (JsPath \ "sessionId").read[String] and
      (JsPath \ "deviceId").readNullable[String]
    )((product, instance, username, actionType, dependencyId, netType, sessionId, deviceId) => LogEntry(product = product,
    instance = instance, username = username, actionType = actionType, dependencyId = dependencyId, netType = netType, sessionId = sessionId, deviceId = deviceId))
}

