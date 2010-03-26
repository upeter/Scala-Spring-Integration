package org.jsi.domain.jpa

import org.hibernate._
import java.sql._
import org.springframework.orm.jpa._
import org.springframework.orm.jpa.support._
import scala.collection.jcl.Conversions._
import java.io._
import javax.persistence._
import java.lang.reflect._
import javax.transaction._
import reflect._

trait JpaPersistable[T] extends JpaDaoSupport {
  def getEntity: T;

  private def getEntityClass: java.lang.Class[T] = {
    getClass().getGenericInterfaces()(0).asInstanceOf[ParameterizedType].getActualTypeArguments()(0).asInstanceOf[java.lang.Class[T]]
  }

  implicit def jpaCallbackWrapper[T](func: (EntityManager) => T) = {
    new JpaCallback {
      def doInJpa(session: EntityManager) = func(session).asInstanceOf[Object]
    }
  }

  def findAll(): List[T] = {
    getJpaTemplate().find("from " + getEntityClass.getName).toList.asInstanceOf[List[T]]
  }

  def save(): T = {
    getJpaTemplate().persist(getEntity)
    getEntity
  }

  def remove() = {
    getJpaTemplate().remove(getEntity);
  }


  def findById(id: Serializable): T = {
    getJpaTemplate().find(getEntityClass, id).asInstanceOf[T];
  }

  def findByName(name: String) = {
    getJpaTemplate().executeFind((em: EntityManager) => {
      val query = em.createQuery("SELECT p FROM Person p WHERE p.name like :name");
      query.setParameter("name", "%" + name + "%");
      query.getResultList();
    }).toList.asInstanceOf[List[T]]
  }
}

  


