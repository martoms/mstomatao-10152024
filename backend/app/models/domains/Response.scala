package domains

case class ResponseOK(
  message: String,
  data: Option[Seq[Task]] = None,
  status: String = "ok"
)

object ResponseOK {
  def unapply(res: ResponseOK): Option[(String, Option[Seq[Task]], String)] = {
    Some((res.message, res.data, res.status))
  }
}

case class ResponseError(message: String, status: String = "error")
object ResponseError {
  def unapply(res: ResponseError): Option[(String, String)] = Some((res.message, res.status))
}