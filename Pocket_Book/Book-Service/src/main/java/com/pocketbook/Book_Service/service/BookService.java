package com.pocketbook.Book_Service.service;

import com.pocketbook.Book_Service.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    public List<Book> getAllBooks();

    public Optional<Book> searchBookById(Long id);

    public Optional<Book> searchBookByName(String title);

    public Optional<Book> searchBookByAuthorName(String authorName);

    public Optional<Book> searchBookByGenre(String genre);

    public void deleteBook(Long id);


}
