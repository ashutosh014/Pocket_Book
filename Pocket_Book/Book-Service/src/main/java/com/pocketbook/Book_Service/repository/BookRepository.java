package com.pocketbook.Book_Service.repository;

import com.pocketbook.Book_Service.model.Book;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByAuthorName(String authorName);

    Optional<Book> findByGenre(String genre);

    Optional<Book> findByTitle(String title);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM Book b WHERE b.id= :bookId")
    Book findByIdAndLock(Long bookId);
}

