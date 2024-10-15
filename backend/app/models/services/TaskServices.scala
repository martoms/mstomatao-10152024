package services

import javax.inject._
import java.util.UUID
import scala.concurrent.{ Future, ExecutionContext }

import domains.{ Task, Status }
import Status._
import dao.Tasks

@Singleton
class TaskServices @Inject()(
  val tasks: Tasks
) (
  implicit ec: ExecutionContext
) {

  import tasks._

  def performGetTasks: Future[Either[String, Seq[Task]]] = {
    getTasks map { res => res match {
      case _ if res.size == 0 => Left("No tasks yet")
      case _ => Right(res)
    }}
  }

  def performAddTask(
    name: String, desc: Option[String] = None, status: Status = TODO
  ): Future[Either[String, Int]] = {
    getTask(name, status) flatMap { res => res match
      case _ if res.size > 0 => Future.successful(Left("Duplicate task found"))
      case _ => {
        addTask(name, desc, status).map(Right(_))
      }
    }
  }

  def performUpdateTask(
    id: String, name: Option[String], desc: Option[String], status: Option[Status]
  ): Future[Either[String, Int]] = {
    val uuid = UUID.fromString(id)
    updateTask(uuid, name, desc, status) map { res => res match {
      case _ if res == 0 => Left("Task Update Failed")
      case _ if res > 0 => Right(res)
    }}
  }

  def performRemoveTask(id: String): Future[Either[String, Int]] = {
    deleteTask(UUID.fromString(id)) map { res => res match {
      case _ if res == 0 => Left("Failed to remove task")
      case _ if res > 0 => Right(res)
    }}
  }
}