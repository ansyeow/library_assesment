package ansyeow.library.book.domain;

import java.util.Optional;

public interface BookRepository {
    Optional<Book> findById(Long id);
    Book store(Book book);
}
