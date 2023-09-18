package ansyeow.library.book;

import ansyeow.library.book.domain.BookIsbn;
import ansyeow.library.book.domain.BookIsbnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Optional;

@Repository
public class BookIsbnRepositoryImpl implements BookIsbnRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert insertBookIsbn;

    public BookIsbnRepositoryImpl(DataSource dataSource) {
        insertBookIsbn = new SimpleJdbcInsert(dataSource)
                .withTableName("book_isbn");
    }

    @Override
    public Optional<BookIsbn> findByIsbn(String isbn) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                "select isbn, author, title from book_isbn where isbn = ?",
                (ResultSet rs, int rowNum) -> {
                    return new BookIsbn(rs.getString("isbn"), rs.getString("author"),
                                        rs.getString("title"));
                },
                isbn));
    }

    @Override
    public Map<String, BookIsbn> mapAllByIsbn() {
        return null;
    }

    @Override
    public void store(BookIsbn bookIsbn) {
        final SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("isbn", bookIsbn.isbn())
                .addValue("author", bookIsbn.author())
                .addValue("title", bookIsbn.title());
        insertBookIsbn.execute(parameters);
    }
}
