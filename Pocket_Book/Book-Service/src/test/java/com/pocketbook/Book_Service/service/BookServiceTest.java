package com.pocketbook.Book_Service.service;

import com.pocketbook.Book_Service.model.Book;
import com.pocketbook.Book_Service.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;

    @BeforeEach
    void setup() {
        book = Book.builder()
                .id(1L)
                .title("Test Book")
                .authorName("Test Author")
                .price(100)
                .genre("Test")
                .build();
    }

    @Test
    void createBook_saveBookSuccessfully() {
        //Mockito.when(bookRepository.findAll()).thenReturn(List.of());

        bookService.saveBook(book);
        Mockito.verify(bookRepository, Mockito.times(1)).save(book);
    }

    @Test
    void getAllBooks_fetchBooksSuccessfully() {
        Mockito.when(bookRepository.findAll()).thenReturn(List.of(book));
        List<Book> bookList = bookService.getAllBooks();
        Assertions.assertFalse(bookList.isEmpty());
        Assertions.assertEquals(1, bookList.size());
        Mockito.verify(bookRepository,Mockito.times(1)).findAll();
    }

    @Test
    void searchBookByTitle_getBookSuccessfully() {
        Mockito.when(bookRepository.findByTitle("Test Book")).thenReturn(Optional.of(book));
        Optional<Book> bookDetails = bookService.searchBookByName("Test Book");
        Assertions.assertFalse(bookDetails.isEmpty());
        Mockito.verify(bookRepository,Mockito.times(1)).findByTitle("Test Book");
    }

}
