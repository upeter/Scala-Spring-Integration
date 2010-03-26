package org.jsi.domain.jpa

import org.springframework.orm.jpa._;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
import scala.collection.jcl._
import javax.persistence._
import org.scalatest.Assertions._

class AbstractDatabaseTestCase extends AbstractTransactionalDataSourceSpringContextTests {
  var jpaTemplate: JpaTemplate = null

  override def getConfigLocations(): Array[String] = {
    Array("ctx-jpa.xml", "ctx-datasource.xml")
  }


  def setEntityManagerFactory(entityManagerFactory: EntityManagerFactory) = {
    this.jpaTemplate = new JpaTemplate(entityManagerFactory);

  }

  def testDummy() {}

}
