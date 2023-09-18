package ansyeow.library.book;

import ansyeow.library.book.domain.Book;
import ansyeow.library.book.domain.BookIsbn;
import ansyeow.library.book.domain.BookIsbnRepository;
import ansyeow.library.book.domain.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryImpl implements BookRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert insertBook;

    public BookRepositoryImpl(DataSource dataSource) {
        insertBook = new SimpleJdbcInsert(dataSource)
                .withTableName("book").usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<Book> findById(BookIsbnRepository bookIsbnRepository, Long id) {
        final Optional<String> isbnOpt =
                Optional.ofNullable(jdbcTemplate.queryForObject(
                        "select isbn from book where id = ?",
                        (ResultSet rs, int rowNum) -> {
                            return rs.getString("isbn");
                        },
                        id));
        if (isbnOpt.isEmpty()) {
            return Optional.empty();
        }

        final Optional<BookIsbn> bookIsbnOpt =
                bookIsbnRepository.findByIsbn(isbnOpt.get());
        Assert.isTrue(bookIsbnOpt.isPresent(),
                String.format("bookIsbn[%s] not found. bookId[%d]", isbnOpt.get(), id));

        return Optional.of(new Book(id, bookIsbnOpt.get()));
    }

    @Override
    public List<Book> findAll(BookIsbnRepository bookIsbnRepository) {
        return null;
    }

    @Override
    public Book store(Book book) {
        final SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("isbn", book.bookIsbn().isbn());
        Number genId = insertBook.executeAndReturnKey(parameters);
        return new Book(genId.longValue(), book.bookIsbn());
    }
}
