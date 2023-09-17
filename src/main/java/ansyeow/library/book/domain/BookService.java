package ansyeow.library.book.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookIsbnRepository bookIsbnRepository;
    @Autowired
    private BookRepository bookRepository;

    void registerNewBook(BookIsbn newBookIsbn) {
        final Optional<BookIsbn> dbBookIsbnOpt =
                bookIsbnRepository.findByIsbn(newBookIsbn.isbn());
        if (!dbBookIsbnOpt.isPresent()) {
            bookIsbnRepository.store(newBookIsbn);
        }
        final Book newBook = new Book(null, newBookIsbn);
        bookRepository.store(newBook);
    }

    Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }
}
