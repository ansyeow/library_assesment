package ansyeow.library.book;

import ansyeow.library.book.domain.Book;
import ansyeow.library.book.domain.BookIsbn;

import java.util.Optional;

public interface BookService {

    Book registerNewBook(BookIsbn newBookIsbn);

    Optional<Book> findById(Long id);
}
