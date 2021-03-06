package myapp

class Book {

    static belongsTo = Author

    String name

    Author author

    static mapping = {
        datasource "dataSource2"
    }

    Book withAuthor(Author author) {
        this.author = author
        return this
    }

}
