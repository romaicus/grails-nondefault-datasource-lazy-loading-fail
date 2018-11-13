package myapp

import grails.core.GrailsApplication

class BootStrap {

    MyService myService
    def grailsApplication

    def init = { servletContext ->
        myService.init()
        myService.verify()


        def properties = grailsApplication.config.toProperties()

        log.info("Properties:")
        properties.propertyNames().each { String pName ->
            log.info(pName + " -> " + properties.getProperty(pName))
        }

    }
    def destroy = {
    }
}
