package utilities.implicits

import slick.jdbc.H2Profile.api._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import java.util.UUID

import domains._
import Status._

given BaseColumnType[Status] = {
  MappedColumnType.base[Status, String](
    status => status match {
      case TODO => "TO DO"
      case INPROGRESS => "IN PROGRESS"
      case DONE => "DONE"
    },
    string => string match {
      case "TO DO" => TODO
      case "IN PROGRESS" => INPROGRESS
      case "DONE" => DONE
    },
  )
}

given Reads[Status] = Reads[Status](json => {
  json.validate[String].flatMap {
    case "TO DO"       => JsSuccess(TODO)
    case "IN PROGRESS" => JsSuccess(INPROGRESS)
    case "DONE"       => JsSuccess(DONE)
    case invalid       => JsError(s"Invalid status: $invalid")
  }
})

given Reads[Add] = (
  (__ \ "name").read[String] and
  (__ \ "description").readNullable[String] and
  (__ \ "status").read[Status]
)(Add.apply _)

given Reads[Update] = (
  (__ \ "name").readNullable[String] and
  (__ \ "description").readNullable[String] and
  (__ \ "status").readNullable[Status]
)(Update.apply _)

given Writes[Status] = Writes[Status] {
  case TODO => JsString("TO DO")
  case INPROGRESS => JsString("IN PROGRESS")
  case DONE => JsString("DONE")
}

given Writes[Task] = (
  (__ \ "name").write[String] and
  (__ \ "description").writeOptionWithNull[String] and
  (__ \ "status").write[Status] and
  (__ \ "id").write[UUID]
)(unlift(Task.unapply))

given Writes[ResponseOK] = (
  (__ \ "message").write[String] and
  (__ \ "data").writeOptionWithNull[Seq[Task]] and
  (__ \ "status").write[String]
)(unlift(ResponseOK.unapply))

given Writes[ResponseError] = (
  (__ \ "message").write[String] and
  (__ \ "status").write[String]
)(unlift(ResponseError.unapply))