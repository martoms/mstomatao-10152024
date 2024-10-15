package dao

import javax.inject._
import play.api.db.slick._
import slick.jdbc.JdbcProfile

import java.util.UUID
import scala.concurrent.{ Future, ExecutionContext }

import repo.TasksRepo
import domains.{Task, Status}
import utilities.implicits.given
import Status._


@Singleton
class Tasks @Inject()(
  protected val dbConfigProvider: DatabaseConfigProvider,
  val tasksRepo: TasksRepo
) (
  implicit ec: ExecutionContext
) extends HasDatabaseConfigProvider[JdbcProfile] {

  import dbConfig.profile.api._
  import tasksRepo.tasks

  def getTasks: Future[Seq[Task]] = db.run(tasks.result)

  def getTask(name: String, status: Status): Future[Seq[Task]] = {
    val action: DBIO[Seq[Task]] = tasks.filter(t => t.name === name && t.status === status).result
    db.run(action)
  }

  def addTask(name: String, desc: Option[String] = None, status: Status = TODO): Future[Int] = {
    val action: DBIO[Int] = tasks += Task(name, desc, status)
    db.run(action)
  }

  def updateTask(id: UUID, name: Option[String], desc: Option[String], status: Option[Status]): Future[Int] = {
    val query = tasks.filter(_.id === id)

    (name, desc, status) match {
      case (Some(_), Some(_), Some(_)) => {
        val action: DBIO[Int] = query.map(t => (t.name, t.description, t.status))
        .update((name.get, desc, status.get))
        db.run(action)
      }
      case (Some(_), Some(_), None) => {
        val action: DBIO[Int] = query.map(t => (t.name, t.description))
        .update((name.get, desc))
        db.run(action)
      }
      case (Some(_), None, Some(_)) => {
        val action: DBIO[Int] = query.map(t => (t.name, t.status))
        .update((name.get, status.get))
        db.run(action)
      }
      case (None, Some(_), Some(_)) => {
        val action: DBIO[Int] = query.map(t => (t.description, t.status))
        .update((desc, status.get))
        db.run(action)
      }
      case (Some(_), None, None) => {
        val action: DBIO[Int] = query.map(_.name)
        .update(name.get)
        db.run(action)
      }
      case (None, Some(_), None) => {
        val action: DBIO[Int] = query.map(_.description)
        .update(desc)
        db.run(action)
      }
      case (None, None, Some(_)) => {
        val action: DBIO[Int] = query.map(_.status)
        .update(status.get)
        db.run(action)
      }
    }
  }

  def deleteTask(id: UUID): Future[Int] = {
    val action: DBIO[Int] = tasks.filter(_.id === id).delete
    db.run(action)
  }
}