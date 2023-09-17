package ansyeow.library.book.domain;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Optional<Book> findById(BookIsbnRepository bookIsbnRepository,
                            Long id);

    List<Book> findAll(BookIsbnRepository bookIsbnRepository);

    Book store(Book book);
}
