package org.jsi.domain.jpa

import org.springframework.orm.jpa._
import org.jsi.domain.{PersonDao, Person}

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
import javax.persistence._
import scala.collection.jcl._
import org.scalatest._
import org.scalatest.junit.JUnit3Suite
import org.scalatest.Assertions._
import reflect._

class JpaPersonDaoTest extends AbstractDatabaseTestCase {
  @BeanProperty var personDao: PersonDao = null

  def testShouldAddAndLinkPersonsViaDao {
    val p1 = Person("Rod Johnsen")
    val p2 = Person("Martin Odersky")
    p1.link(p2)

    personDao.save(p1)
    personDao.save(p2)

    val persons = personDao.findAll()

    expect(2)(persons size)
    assert(persons(0).notificationService != null)
    assert(persons.exists(_.name == "Rod Johnsen"))
    assert(persons.exists(_.name == "Martin Odersky"))
  }

  def testShouldHaveNotificationServiceInjectedViaDao {
    val p1 = Person("Rod Johnsen")
    personDao.save(p1)

    val persons = personDao.findAll()

    expect(1)(persons size)
    assert(persons(0).notificationService != null)
    assert(persons.exists(_.name == "Rod Johnsen"))
  }

  def testShouldHaveNotificationServiceInjectedViaConstructor {
    val p1 = Person("Rod Johnsen")
    assert(p1.notificationService != null)
  }

  def testShouldAddAndLinkPersonViaItself {
    val name1 = "Rod Johnsen"
    val name2 = "Martin Odersky"
    val p1 = Person(name1).save
    val p2 = Person(name2).save

    p2.link(p1)
    p1.link(p2)
    p1.save
    p2.save

    //check if persons are persisted correclty
    var found1 = Person().findByName(name1)
    expect(1)(found1.size)
    expect(1) {found1(0).getRelations.size}

    var found2 = Person().findByName(name2)
    expect(1)(found2.size)
    expect(1) {found2(0).getRelations.size}

    val l2 = Person().findAll()
    expect(2) {l2.size}
  }


}
