package org.jsi.domain.jpa

import javax.persistence._
import java.sql._
import org.springframework.orm.jpa._
import org.springframework.orm.jpa.support._
import scala.collection.jcl.Conversions._
import java.io._
import org.jsi.domain._
import AbstractScalaJpaDaoSupport._

object AbstractScalaJpaDaoSupport {
  implicit def jpaCallbackWrapper[T](func: (EntityManager) => T) = {
    new JpaCallback {
      def doInJpa(session: EntityManager) = func(session).asInstanceOf[Object]
    }
  }
}

class AbstractScalaJpaDaoSupport[T](val persistentClass: Class[T]) extends JpaDaoSupport with GenericDao[T] {
  def findAll(): List[T] = {
    getJpaTemplate().find("from " + persistentClass.getName).toList.asInstanceOf[List[T]]
  }

  def save(entity: T): T = {
    getJpaTemplate().persist(entity)
    entity
  }

  def remove(entity: T) = {
    getJpaTemplate().remove(entity);
  }

  def findById(id: Serializable): T = {
    getJpaTemplate().find(persistentClass, id).asInstanceOf[T];
  }

}


