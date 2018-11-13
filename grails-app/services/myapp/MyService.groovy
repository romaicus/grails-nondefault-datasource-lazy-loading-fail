package myapp

import grails.gorm.transactions.Transactional

@Transactional
class MyService {

    void init() {

        User admin = new User(username: "admin", password: "admin", enabled: true, type: "ADMIN").save(failOnError: true)
        User user1 = new User(username: "user1", password: "userpass", enabled: true, type: "USER").save(failOnError: true)

        Role adminRole = new Role(authority: "ROLE_ADMIN").save(failOnError: true)
        Role userRole = new Role(authority: "ROLE_USER").save(failOnError: true)

        UserRole.create(admin, adminRole)
        UserRole.create(admin, userRole)

        UserRole.create(user1, userRole)

        /**
         *  Author 1 <------> * Book
         */

        Author authorOne = new Author(name: "AuthorOne")
        authorOne.save()

        Book book1 = new Book(name: "BookOne", author: authorOne)
        book1.save()

        Book book2 = new Book(name: "BookTwo", author: authorOne)
        book2.save()

        Book book3 = new Book(name: "BookThree", author: authorOne)
        book3.save()

        Book book4 = new Book(name: "BookFour", author: authorOne)
        book4.save()

        Book book5 = new Book(name: "BookFive", author: authorOne)
        book5.save()

    }

    void verify() {
        assert Author.first().name == "AuthorOne"
    }

}
