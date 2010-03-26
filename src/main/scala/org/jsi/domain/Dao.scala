package org.jsi.domain

import java.io._

trait GenericDao[T] {
  def findAll(): List[T]

  def save(entity: T): T

  def remove(entity: T): Unit

  def findById(id: Serializable): T

}

trait PersonDao extends GenericDao[Person] {
  def findByName(name: String): List[Person]
}
