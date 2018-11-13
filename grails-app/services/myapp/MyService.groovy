package myapp

import grails.gorm.transactions.Transactional

@Transactional
class MyService {

    void init() {

        User admin = new User(username: "xxxxx", password: "xxxxx", enabled: true, type: "ADMIN").save(failOnError: true)
        User user1 = new User(username: "user1", password: "userpass", enabled: true, type: "USER").save(failOnError: true)

        Role adminRole = new Role(authority: "ROLE_ADMIN").save(failOnError: true)
        Role userRole = new Role(authority: "ROLE_USER").save(failOnError: true)

        UserRole.create(admin, adminRole)
        UserRole.create(admin, userRole)

        UserRole.create(user1, userRole)

        /**
         *  Author 1 <------> * Book
         */

        Author authorOne = new Author(name: "AuthorOne").save()
        new Book(name: "BookOne").withAuthor(authorOne).save()
        new Book(name: "BookTwo").withAuthor(authorOne).save()
        new Book(name: "BookThree").withAuthor(authorOne).save()
        new Book(name: "BookFour").withAuthor(authorOne).save()
        new Book(name: "BookFive").withAuthor(authorOne).save()

        Author authorTwo = new Author(name: "AuthorTwo").save()
        new Book(name: "BookOne").withAuthor(authorTwo).save()
        new Book(name: "BookTwo").withAuthor(authorTwo).save()
        new Book(name: "BookThree").withAuthor(authorTwo).save()
        new Book(name: "BookFour").withAuthor(authorTwo).save()
        new Book(name: "BookFive").withAuthor(authorTwo).save()

        Author authorThree = new Author(name: "AuthorThree").save()
        new Book(name: "BookOne").withAuthor(authorThree).save()
        new Book(name: "BookTwo").withAuthor(authorThree).save()
        new Book(name: "BookThree").withAuthor(authorThree).save()
        new Book(name: "BookFour").withAuthor(authorThree).save()
        new Book(name: "BookFive").withAuthor(authorThree).save()

        Author authorFour = new Author(name: "AuthorFour").save()
        new Book(name: "BookOne").withAuthor(authorFour).save()
        new Book(name: "BookTwo").withAuthor(authorFour).save()
        new Book(name: "BookThree").withAuthor(authorFour).save()
        new Book(name: "BookFour").withAuthor(authorFour).save()
        new Book(name: "BookFive").withAuthor(authorFour).save()

        Author authorFive = new Author(name: "AuthorFive").save()
        new Book(name: "BookOne").withAuthor(authorFive).save()
        new Book(name: "BookTwo").withAuthor(authorFive).save()
        new Book(name: "BookThree").withAuthor(authorFive).save()
        new Book(name: "BookFour").withAuthor(authorFive).save()
        new Book(name: "BookFive").withAuthor(authorFive).save()


    }

    void verify() {
        assert Author.first().name == "AuthorOne"
    }

}
