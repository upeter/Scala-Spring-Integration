package org.jsi.di.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;


public class RichDomainObjectFactory implements BeanFactoryAware {

    private static final Log LOG = LogFactory.getLog(RichDomainObjectFactory.class);

    private AutowireCapableBeanFactory factory = null;

    public <T> T createAndAutowire(Class<T> clazz) {
        T instance = null;
        try {
            instance = clazz.newInstance();
            autowire(instance);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return instance;
    }

    public void autowire(Object instance) {
        if (factory != null) {
            factory.autowireBeanProperties(instance, AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, false);
        } else {
            LOG.warn("No " + AutowireCapableBeanFactory.class.getName() + " has been defined. Autoworing will not work.");
        }
    }

    public void setBeanFactory(BeanFactory factory) throws BeansException {
        this.factory = (AutowireCapableBeanFactory) factory;
    }

    private static RichDomainObjectFactory singleton = new RichDomainObjectFactory();

    public static RichDomainObjectFactory autoWireFactory() {
        return singleton;
    }

    public static void setInstance(RichDomainObjectFactory richDomainObjectFactory) {
        singleton = richDomainObjectFactory;
    }

}
