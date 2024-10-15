package repo

import javax.inject._
import java.util.UUID
import slick.jdbc.H2Profile.api._

import domains.{ Task, Status }
import utilities.implicits.given


@Singleton
class TasksRepo {

  class TasksTable(tag: Tag) extends Table[Task](tag, "TASKS") {
    def id = column[UUID]("ID", O.PrimaryKey)
    def name = column[String]("NAME", O.Length(250))
    def status = column[Status]("STATUS", O.SqlType("ENUM"))
    def description = column[Option[String]]("DESCRIPTION", O.Length(1000))
    def * = (name, description, status, id).mapTo[Task]
  }

  val tasks = TableQuery[TasksTable]
}