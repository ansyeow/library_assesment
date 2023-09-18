package ansyeow.library.checkout;

import ansyeow.library.checkout.domain.CheckOut;
import ansyeow.library.checkout.domain.CheckOutHistory;
import ansyeow.library.checkout.domain.CheckOutHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;

@Repository
public class CheckOutHistoryRepositoryImpl implements CheckOutHistoryRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert insertCheckOutHistory;

    public CheckOutHistoryRepositoryImpl(DataSource dataSource) {
        insertCheckOutHistory = new SimpleJdbcInsert(dataSource)
                .withTableName("check_out_history");
    }

    @Override
    public void store(CheckOutHistory checkOutHistory) {
        final CheckOut checkOut = checkOutHistory.checkOut();
        final SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("borrower_id",    checkOut.borrower().id())
                .addValue("book_id",        checkOut.book().id())
                .addValue("check_out_time", Timestamp.valueOf(checkOut.checkOutTime().toLocalDateTime()))
                .addValue("return_time", Timestamp.valueOf(checkOutHistory.returnTime().toLocalDateTime()));
        insertCheckOutHistory.execute(parameters);
    }
}
