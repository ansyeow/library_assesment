package ansyeow.library.book.domain;

import ansyeow.library.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookIsbnRepository bookIsbnRepository;
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book registerNewBook(BookIsbn newBookIsbn) {
        final Optional<BookIsbn> dbBookIsbnOpt =
                bookIsbnRepository.findByIsbn(newBookIsbn.isbn());

        if (dbBookIsbnOpt.isEmpty()) {
            bookIsbnRepository.store(newBookIsbn);
        } else {
            Assert.isTrue(newBookIsbn.equals(dbBookIsbnOpt.get()),
                    "2 books with the same ISBN numbers must " +
                            "have the same title and same author");
        }

        final Book newBook = new Book(null, newBookIsbn);
        return bookRepository.store(newBook);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(bookIsbnRepository, id);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll(bookIsbnRepository);
    }
}
