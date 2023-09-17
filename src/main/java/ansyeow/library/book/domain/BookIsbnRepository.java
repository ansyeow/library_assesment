package ansyeow.library.book.domain;

import java.util.Map;
import java.util.Optional;

public interface BookIsbnRepository {
    Optional<BookIsbn> findByIsbn(String isbn);

    Map<String,BookIsbn> mapAllByIsbn();

    void store(BookIsbn bookIsbn);
}
