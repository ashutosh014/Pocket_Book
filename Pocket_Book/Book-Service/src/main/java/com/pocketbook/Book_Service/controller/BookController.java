package com.pocketbook.Book_Service.controller;




import com.pocketbook.Book_Service.model.Book;
import com.pocketbook.Book_Service.service.BookServiceIMPL;
import com.pocketbook.Book_Service.service.TestLockingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookServiceIMPL bookService;
    private final TestLockingService testLockingService;

    public BookController(BookServiceIMPL bookService, TestLockingService testLockingService) {
        this.bookService = bookService;
        this.testLockingService = testLockingService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookService.searchBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{title}/availability")
    public ResponseEntity<Boolean> isBookAvailable(@PathVariable String title) {
        boolean available = bookService.isBookAvailable(title);
        return ResponseEntity.ok(available);
    }

  @PutMapping("/{title}/booked")
  public ResponseEntity<String> titleBooking(@PathVariable String title){
       return ResponseEntity.ok(bookService.bookTitle(title));

  }

    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody Book book) {
        Book savedBook = bookService.saveBook(book);
        if (savedBook == null) {
            return new ResponseEntity<>("Book is already available", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(savedBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        if (!bookService.searchBookById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        book.setId(id);
        Book updatedBook = bookService.saveBook(book);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (!bookService.searchBookById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/accessBook")
    public String accessBook(@RequestParam Long bookId, @RequestParam Long userId) throws InterruptedException {
        testLockingService.testLocking(bookId, userId);
        return "Test Completed";
    }
}

