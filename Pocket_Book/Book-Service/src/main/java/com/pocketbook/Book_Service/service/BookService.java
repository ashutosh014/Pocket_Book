package com.pocketbook.Book_Service.service;

import com.pocketbook.Book_Service.model.Book;
import com.pocketbook.Book_Service.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private  final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> searchBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Optional<Book> searchBookByName(String title) {
        return bookRepository.findByTitle(title);
    }
    public Optional<Book> searchBookByAuthorName(String authorName) {
        return bookRepository.findByAuthorName(authorName);
    }

    public Optional<Book> searchBookByGenre(String genre) {
        return bookRepository.findByGenre(genre);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public Optional<Book> searchBookByuserId(Long user_id) {
        return bookRepository.findById(user_id);
    }
}

