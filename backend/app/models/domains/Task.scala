package domains

import java.util.UUID

enum Status {
  case TODO, INPROGRESS, DONE
}
import Status._

case class Add(
  name: String,
  description: Option[String] = None,
  status: Status = TODO
)

object Add {
  def unapply(task: Task): Option[(String, Option[String], Status)] = {
    Some((task.name, task.description, task.status))
  }
}

case class Update(
  name: Option[String] = None,
  description: Option[String] = None,
  status: Option[Status] = None
)

object Update {
  def unapply(task: Update): Option[(Option[String], Option[String], Option[Status])] = {
    Some((task.name, task.description, task.status))
  }
}

case class Task(
  name: String,
  description: Option[String] = None,
  status: Status = TODO,
  id: UUID = UUID.randomUUID
)

object Task {
  def unapply(task: Task): Option[(String, Option[String], Status, UUID)] = {
    Some((task.name, task.description, task.status, task.id))
  }
}