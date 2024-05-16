#### Book Api


### API's
1. POST   - api/v1/book
2. GET    - api/v1/book/{Name}
          - api/v1/books
3. Patch  - api/v1/book/update/{name}
4. Delete - api/v1/book/delete/{name}



#### Entity

- Book
    - name (id)
    - author
    - publisher
    - price

#### Controller

- POST
    - create(BOOK) - create a new book
- GET
    - getBook(name) - get the book of specified name
    - getAllBooks()  - get all books
- PATCH
    - updateBook(name, publisher) - to update publisher of book
- Delete
    - deleteBook(book name) - to delete a book

#### Service

- getAllBooks() - to get all books
- create(Book book) - to create a new book
- getBookById(name) - to get book by id
- deleteBook(book name) - to delete a book
- updateBook(book name, publisher name) - to update a book publisher name

#### Repository

- BookRepository extends MongooseRepository<String, Book> 