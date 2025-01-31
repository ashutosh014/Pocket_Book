package com.pocketbook.Book_Service.service;

import com.pocketbook.Book_Service.model.Book;
import com.pocketbook.Book_Service.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceIMPL implements BookService{

    private final BookRepository bookRepository;

    public BookServiceIMPL(BookRepository bookRepository) {
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


    // Use pessimistic locking in the book row
    // so that only one user should be able to
    // access the book at a time
    @Transactional
    public void assignBookToUser(Long bookId, Long userId) {
        /*
        * Find the book first using the bookId
        * Check if the book is available
        * */
        System.out.println(Thread.currentThread().getName() + " is attempting to fetch the book details");
        Book book = bookRepository.findByIdAndLock(bookId);
        System.out.println(Thread.currentThread().getName() + " acquired the lock for book: " + bookId);

        // Check if the book is available
        if(!book.isBookAvailable()) {
            throw new RuntimeException("Book is already being accessed by a different user");
        }

        System.out.println(Thread.currentThread().getName() + " is acquiring the access for the book :" + bookId +  " for user: " + userId);
        book.setBookAvailable(false);
        book.setUser_id(userId);

        bookRepository.save(book);
        System.out.println(Thread.currentThread().getName() + " has successfully reserved the book : " + bookId + " for user : " + userId);
    }

}


