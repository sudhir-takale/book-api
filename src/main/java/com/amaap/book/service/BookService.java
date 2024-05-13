package com.amaap.book.service;

import com.amaap.book.entity.Book;
import com.amaap.book.repository.BookRepository;
import com.amaap.book.service.exception.BookNotFoundException;
import com.amaap.book.service.exception.InvalidBookException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;


    public Book createBook(Book book) {

        return bookRepository.save(book);
    }

    public List<Book> getBooks() {

        return bookRepository.findAll();
    }

    public Book getBook(String name) throws BookNotFoundException {
        Optional<Book> book = bookRepository.findById(name);

        if (book.isPresent()) {
            return book.get();
        } else {
            throw new BookNotFoundException("Book not found with name: " + name);
        }
    }

    public int removeBook(String name) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(name);
        if (bookOptional.isPresent()) {
            bookRepository.deleteById(name);
            return 1;
        } else {

            throw new BookNotFoundException("Book not found with " + name);
        }

    }


    public Book update(String name, String publisher) throws InvalidBookException, BookNotFoundException {
        Optional<Book> existingBook = bookRepository.findById(name);
        if (publisher.isEmpty()) {
            throw new InvalidBookException("Invalid publisher for book " + name);
        }
        if (existingBook.isEmpty()) {
            throw new BookNotFoundException("Book with name " + name + " not found");
        }
        existingBook
                .get()
                .setPublisher(publisher);
        bookRepository.save(existingBook.get());
        return existingBook.get();
    }

}
