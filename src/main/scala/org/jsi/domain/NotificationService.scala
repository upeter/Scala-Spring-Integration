package org.jsi.domain

sealed trait Notification

case class PersonLinkageNotification(person: Person, relation: Person) extends Notification


trait NotificationService {
  def nofity(notification: Notification): Unit
}

class NotificationServiceImpl extends NotificationService {
  def nofity(notification: Notification) = {
    notification match {
      case PersonLinkageNotification(person, relation) =>
        println("Notify " + relation.name + " that he/she is now linked to " + person.name)
      case _ =>
        println("...")
    }
  }
}
    