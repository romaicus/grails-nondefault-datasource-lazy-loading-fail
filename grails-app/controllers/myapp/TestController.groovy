package myapp

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN', 'ROLE_USER'])
@Transactional("dataSource2")
class TestController {

    def index() {

        def authorCriteria = Author.createCriteria()

        List<Author> authors = authorCriteria.list(max: 3) {}

        /*authors.each { author ->
            author.books.each { book ->
                log.info("Author: ${author.name} Book: ${book.name}")
            }
        }*/

        [authors: authors]
    }
}
