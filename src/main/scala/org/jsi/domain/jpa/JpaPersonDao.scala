package org.jsi.domain.jpa

import javax.persistence._
import java.sql._
import org.springframework.orm.jpa._
import org.springframework.orm.jpa.support._
import scala.collection.jcl.Conversions._
import java.util._
import org.jsi.domain._
import AbstractScalaJpaDaoSupport._

class JpaPersonDao extends AbstractScalaJpaDaoSupport(classOf[Person]) with PersonDao {
  def findByName(name: String) = {
    getJpaTemplate().executeFind((em: EntityManager) => {
      val query = em.createQuery("SELECT p FROM Person p WHERE p.name like :name");
      query.setParameter("name", "%" + name + "%");
      query.getResultList();
    }).asInstanceOf[List[Person]].toList
  }


  //      public List<T> findAll() {
  //        return getHibernateTemplate().executeFind(new HibernateCallback() {
  //            public List doInHibernate(Session session) throws HibernateException, SQLException {
  //                Criteria criteria = session.createCriteria(persistentClass);
  //                if (getDefaultOrder() != null) {
  //                    criteria = criteria.addOrder(getDefaultOrder());
  //                }
  //                return criteria.list();
  //            }
  //        });
}
