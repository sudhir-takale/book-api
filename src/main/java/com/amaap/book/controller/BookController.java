package com.amaap.book.controller;

import com.amaap.book.entity.Book;
import com.amaap.book.service.BookService;
import com.amaap.book.service.exception.BookNotFoundException;
import com.amaap.book.service.exception.InvalidBookException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/book")
    @ResponseBody
    public ResponseEntity<Object> create(@RequestBody Book book) {
        try {
            bookService.createBook(book);
            return ResponseEntity.ok(book);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body("Book has not inserted " + book);
        }
    }


    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping("/book/{name}")
    public ResponseEntity<Object> getBook(@PathVariable String name) {
        try {
            Book book = bookService.getBook(name);
            return ResponseEntity.ok(book);
        } catch (BookNotFoundException e) {
            return ResponseEntity
                    .badRequest()
                    .body("Book not found " + name);

        }
    }

    @DeleteMapping("/book/delete/{name}")
    public ResponseEntity<String> deleteBook(@PathVariable String name) {

        try {
            bookService.removeBook(name);
            return ResponseEntity.ok("Book deleted successfully");
        } catch (BookNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @PatchMapping("/book/update/{name}")
    public ResponseEntity<Object> updateBook(@PathVariable String name,
                                             @RequestParam(required = false) String publisher) {
        try {
            Book book = bookService.update(name, publisher);
            return ResponseEntity.ok(book);
        } catch (InvalidBookException e) {
            return ResponseEntity
                    .badRequest()
                    .body("Invalid publisher for book " + name);
        } catch (BookNotFoundException e) {
            return ResponseEntity
                    .badRequest()
                    .body("Book with name " + name + " not found");
        }
    }


}



