package com.pocketbook.Book_Service.repository;

import com.pocketbook.Book_Service.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {



    Optional<Book> findByAuthorName(String authorName);

    Optional<Book> findByGenre(String genre);

    Optional<Book> findByTitle(String title);
}
