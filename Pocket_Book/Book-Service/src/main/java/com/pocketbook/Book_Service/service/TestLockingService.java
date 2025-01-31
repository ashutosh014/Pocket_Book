package com.pocketbook.Book_Service.service;

import org.springframework.stereotype.Service;

@Service
public class TestLockingService {

    private final BookServiceIMPL bookService;

    public TestLockingService(BookServiceIMPL bookService) {
        this.bookService = bookService;
    }

    public void testLocking(Long bookId, Long userId) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            try {
                bookService.assignBookToUser(bookId, userId);
            }
            catch (Exception e) {
                System.out.println(Thread.currentThread().getName() + " failed " + e);
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                bookService.assignBookToUser(bookId, 3L);
            }
            catch (Exception e) {
                System.out.println(Thread.currentThread().getName() + " failed " + e);
            }
        });

        Thread thread3 = new Thread(() -> {
            try {
                bookService.assignBookToUser(bookId, 2L);
            }
            catch (Exception e) {
                System.out.println(Thread.currentThread().getName() + " failed " + e);
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();
    }

}
