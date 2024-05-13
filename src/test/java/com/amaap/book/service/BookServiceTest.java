package com.amaap.book.service;

import com.amaap.book.entity.Book;
import com.amaap.book.repository.BookRepository;
import com.amaap.book.service.exception.BookNotFoundException;
import com.amaap.book.service.exception.InvalidBookException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookServiceTest {

    @MockBean
    BookRepository bookRepository;

    @Autowired
    BookService bookService;

    @Test
    void shouldBeAbleToCreateABook() throws InvalidBookException {
        Book book = new Book("Clean code", "uncle bob", "XYZ", 34.3);
        when(bookRepository.save(book)).thenReturn(book);
        Book book1 = bookService.createBook(book);
        assertEquals(34.3, book1.getPrice());
    }


    @Test
    void shouldBeAbleToReturnAllBooks() {
        Book book = new Book("Clean code", "uncle bob", "XYZ", 34.3);
        List<Book> books = new ArrayList<>();
        books.add(book);
        when(bookRepository.findAll()).thenReturn(books);
        List<Book> bookList = bookService.getBooks();
        assertEquals(1, bookList.size());
    }

    @Test
    void shouldBeAbleToGetBookByName() {
        Book book = new Book("Clean code", "uncle bob", "XYZ", 34.3);
        when(bookRepository.findById("Clean Code")).thenReturn(Optional.of(book));
        Optional<Book> book1 = bookRepository.findById("Clean Code");
        assertEquals(book, book1.get());
    }


    @Test
    void shouldThrowBookNotException() throws InvalidBookException {
        Book book = new Book("Clean code", "uncle bob", "XYZ", 34.3);
        bookService.createBook(book);
        assertThrows(BookNotFoundException.class, () -> bookService.getBook("Code Smell"));

    }


    @Test
    void shouldBeAbleToDeleteABook() {
        Book book = new Book("java", "uncle bob", "XYZ", 34.3);
        bookRepository.save(book);
        bookRepository.deleteById("java");
        verify(bookRepository, times(1)).deleteById("java");
    }


    @Test
    void shouldBeAbleToUpdateBookPublisher() throws InvalidBookException, BookNotFoundException {
        Book book = new Book("java", "uncle bob", "XYZ", 34.3);
        when(bookRepository.findById("java")).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        Book updatedBook = bookService.update("java", "ABC");

        assertEquals("ABC", updatedBook.getPublisher());
    }

    @Test
    void shouldThrowExceptionWhenBookPublisherIsEmpty() {
        Book book = new Book("java", "uncle bob", "XYZ", 34.3);
        assertThrows(InvalidBookException.class, () -> bookService.update("java", ""));
    }

    @Test
    void shouldThrowExceptionIfBookNotFound() throws InvalidBookException {
        Book book = new Book("java", "uncle bob", "XYZ", 34.3);
        bookService.createBook(book);

        assertThrows(BookNotFoundException.class, () -> bookService.update("java Guides", "ABC"));
    }

}