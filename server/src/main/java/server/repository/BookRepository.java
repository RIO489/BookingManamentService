package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.entity.BookEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<BookEntity, UUID> {

    Optional<BookEntity> findByTitle(String title);

    List<BookEntity> findByAuthor(String author);

    Optional<BookEntity> findByIsbn(String isbn);

}