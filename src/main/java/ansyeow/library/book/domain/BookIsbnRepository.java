package ansyeow.library.book.domain;

import java.util.Optional;

public interface BookIsbnRepository {
    Optional<BookIsbn> findByIsbn(String isbn);
    void store(BookIsbn bookIsbn);
}
