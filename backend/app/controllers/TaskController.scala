package controllers

import javax.inject._
import play.api._
import play.api.mvc._

import scala.concurrent.{ Future, ExecutionContext }
import play.api.libs.json._

import domains._
import domains.{ Status as StatusEnum }
import StatusEnum.{ TODO as todo }
import utilities.implicits.given
import services.TaskServices

@Singleton
class TaskController @Inject()(
  cc: ControllerComponents,
  val taskServices: TaskServices
)(
  implicit ec: ExecutionContext
) extends AbstractController(cc) {

  import taskServices._

  def index() = TODO
  def getTasks() = Action.async { implicit request: Request[AnyContent] =>
    performGetTasks map {
      case Left(message) => {
        val json = Json.toJson(ResponseOK(message))
        Ok(json)
      }
      case Right(res) => {
        val json = Json.toJson(ResponseOK("Tasks Found", Some(res)))
        Ok(json)
      }
    }  
  }

  def addTask() = Action.async(parse.json) { implicit request: Request[JsValue] =>
    request.body.validate[Add].fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors)))),
      data => {
        val name = data.name
        val desc = data.description
        val status = data.status
        performAddTask(name, desc, status) flatMap {
          case Left(message) => {
            val json = Json.toJson(ResponseError(message))
            Future.successful(BadRequest(json))
          }
          case Right(_) => {
            performGetTasks flatMap {
              case Left(message) => {
                val json = Json.toJson(ResponseOK(message))
                Future.successful(Ok(json))
              }
              case Right(res) => {
                val json = Json.toJson(ResponseOK("Task added successfully", Some(res)))
                Future.successful(Ok(json))
              }
            }
          }
        }
      }
    )
  }

  def updateTask(id: String) = Action.async(parse.json) { implicit request: Request[JsValue] =>
    request.body.validate[Update].fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors)))),
      data => {
        val name = data.name
        val desc = data.description
        val status = data.status
        performUpdateTask(id, name, desc, status) flatMap {
          case Left(message) => {
            val json = Json.toJson(ResponseError(message))
            Future.successful(BadRequest(json))
          }
          case Right(_) => {
            performGetTasks flatMap {
              case Left(message) => {
                val json = Json.toJson(ResponseOK(message))
                Future.successful(Ok(json))
              }
              case Right(res) => {
                val json = Json.toJson(ResponseOK("Task updated successfully", Some(res)))
                Future.successful(Ok(json))
              }
            }
          }
        }
      }
    )
  }

  def removeTask(id: String) = Action.async { implicit request: Request[AnyContent] =>
    performRemoveTask(id) flatMap {
      case Left(message) => {
        val json = Json.toJson(ResponseError(message))
        Future.successful(BadRequest(json))
      }
      case Right(_) => {
        performGetTasks flatMap {
          case Left(_) => {
            val json = Json.toJson(ResponseOK("Task removed successfully", Some(Seq.empty)))
            Future.successful(Ok(json))
          }
          case Right(res) => {
            val json = Json.toJson(ResponseOK("Task removed successfully", Some(res)))
            Future.successful(Ok(json))
          }
        }
      }
    }
  }
}
