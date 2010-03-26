package org.jsi.domain.hibernate

import org.hibernate.EmptyInterceptor
import org.jsi.di.spring.RichDomainObjectFactory
import java.io.Serializable
import org.hibernate.`type`.Type;

class DependencyInjectionInterceptor extends EmptyInterceptor {
  override def onLoad(instance: Object, id: Serializable, propertieValues: Array[Object], propertyNames: Array[String], propertyTypes: Array[Type]) = {
    RichDomainObjectFactory.autoWireFactory.autowire(instance)
    false
  }

}