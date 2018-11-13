package myapp

class Author {

    static hasMany = [books: Book]

    String name

    static mapping = {
        datasource "dataSource2"
    }

}
