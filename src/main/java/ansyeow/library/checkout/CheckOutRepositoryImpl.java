package ansyeow.library.checkout;

import ansyeow.library.book.domain.Book;
import ansyeow.library.borrower.domain.Borrower;
import ansyeow.library.checkout.domain.CheckOut;
import ansyeow.library.checkout.domain.CheckOutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Optional;

@Repository
public class CheckOutRepositoryImpl implements CheckOutRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert insertCheckOut;

    public CheckOutRepositoryImpl(DataSource dataSource) {
        insertCheckOut = new SimpleJdbcInsert(dataSource)
                .withTableName("check_out");
    }

    @Override
    public Optional<CheckOut> findByBook(Book book, Optional<Borrower> borrowerOpt) {
        final Optional<SimpleImmutableEntry<Long,Timestamp>> checkOutPairOpt =
                Optional.ofNullable(jdbcTemplate.queryForObject(
                        "select borrower_id, check_out_time from check_out where book_id = ?",
                        (ResultSet rs, int rowNum) -> {
                            return new SimpleImmutableEntry<Long,Timestamp>(
                                    rs.getLong("borrower_id"),
                                    rs.getTimestamp("check_out_time"));
                        },
                        book.id()));
        if (checkOutPairOpt.isEmpty()) {
            return Optional.empty();
        }

        borrowerOpt.ifPresent(borrower -> {
            Assert.isTrue(checkOutPairOpt.get().getKey().equals(borrower.id()),
                    "db check_out borrower not same as input borrower");
        });

        Assert.isTrue(borrowerOpt.isPresent(), "input borrower not found");
        ZonedDateTime checkOutTime = checkOutPairOpt.get().getValue().toLocalDateTime()
                .atZone(ZoneId.systemDefault());
        return Optional.of(new CheckOut(borrowerOpt.get(), book, checkOutTime));
    }

    @Override
    public void store(CheckOut checkOut) {
        final SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("borrower_id",    checkOut.borrower().id())
                .addValue("book_id",        checkOut.book().id())
                .addValue("check_out_time", Timestamp.valueOf(checkOut.checkOutTime().toLocalDateTime()));
        insertCheckOut.execute(parameters);
    }

    @Override
    public void discard(CheckOut checkOut) {
    }
}
