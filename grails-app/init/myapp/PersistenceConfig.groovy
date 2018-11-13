package myapp

import org.grails.orm.hibernate.HibernateDatastore
import org.grails.orm.hibernate5.support.GrailsOpenSessionInViewInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PersistenceConfig {

    @Autowired
    HibernateDatastore hibernateDatastore

    @Bean
    GrailsOpenSessionInViewInterceptor openSessionInViewInterceptor_dataSource2() {
        GrailsOpenSessionInViewInterceptor openSessionInViewInterceptor = new GrailsOpenSessionInViewInterceptor()
        HibernateDatastore hibernateDatastore = this.hibernateDatastore.getDatastoreForConnection "dataSource2"
        openSessionInViewInterceptor.hibernateDatastore = hibernateDatastore
        return openSessionInViewInterceptor
    }

}
