package myapp

class BootStrap {

    MyService myService

    def init = { servletContext ->
        myService.init()
        myService.verify()
    }
    def destroy = {
    }
}
