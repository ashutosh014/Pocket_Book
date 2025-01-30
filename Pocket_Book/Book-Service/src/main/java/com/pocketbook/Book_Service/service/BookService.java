package com.pocketbook.Book_Service.service;

import com.pocketbook.Book_Service.model.Book;
import com.pocketbook.Book_Service.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

  //  @Autowired
    private BookRepository bookRepository;

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

    public Optional<Book> searchBookBygenre(String genre) {
        return bookRepository.findBygenre(genre);
    }

    public Book saveBook(Book book) {
        if(book!=null){
          Optional<Book> availableBook =  bookRepository.findByTitle(book.getTitle());
           if(availableBook.isPresent()){
               availableBook.get().getTitle().equals(book.getTitle());
               return null;
           }
        }
        book.setBookAvailable(true);
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public Optional<Book> searchBookByuserId(Long user_id) {
        return bookRepository.findById(user_id);
    }

    public boolean isBookAvailable(String title) {
        Optional<Book> book = bookRepository.findByTitle(title);
        return book.map(Book::isBookAvailable).orElse(false);
    }

    public String bookTitle(String title) {
      Book book = bookRepository.findByTitle(title).get();
       if (book.isBookAvailable()){
           book.setBookAvailable(false);
           bookRepository.save(book);
           return "Title booked!..";
       }
       else{
           return "Book is Not available";
       }
    }
}


