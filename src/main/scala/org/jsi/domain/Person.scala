package org.jsi.domain

import java.util.{Set => JSet}
import java.util.HashSet
import scala.collection.jcl.Conversions._
import reflect._
import org.springframework.context._
import org.springframework.beans.factory._
import org.springframework.beans.factory.config._
import org.jsi.domain.jpa._


class Person extends JpaPersistable[Person] with java.io.Serializable {
  //class Person extends  java.io.Serializable {

  def getEntity = this

  @BeanProperty var notificationService: NotificationService = _

  var id: Long = -1
  var name: String = _
  var email: String = _
  private var relations: JSet[Person] = new HashSet();

  def link(relation: Person) = {
    relations.add(relation)
    notificationService.nofity(PersonLinkageNotification(this, relation))
  }

  def getRelations() = {
    relations.toList
  }

  override def toString() = id + " " + name + " knows: " + getRelations.map(_.name).toString

}

import org.jsi.di.spring.RichDomainObjectFactory._

object Person {
  def apply() = {
    autoWireFactory.createAndAutowire(classOf[Person])
  }

  def apply(name: String) = {
    val p = autoWireFactory.createAndAutowire(classOf[Person])
    p.name = name
    p
  }

}

