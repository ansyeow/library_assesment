package ansyeow.library.borrower;

import ansyeow.library.borrower.domain.Borrower;
import ansyeow.library.borrower.domain.BorrowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.Optional;

@Repository
public class BorrowerRepositoryImpl implements BorrowerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert insertBorrower;

    public BorrowerRepositoryImpl(DataSource dataSource) {
        insertBorrower = new SimpleJdbcInsert(dataSource)
                .withTableName("borrower").usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<Borrower> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                "select id, name, email_address from borrower where id = ?",
                (ResultSet rs, int rowNum) -> {
                    return new Borrower(rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("email_address"));
                },
                id));
    }

    @Override
    public Borrower store(Borrower borrower) {
        final SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", borrower.name())
                .addValue("email_address", borrower.emailAddress());
        Number genId = insertBorrower.executeAndReturnKey(parameters);
        return new Borrower(genId.longValue(), borrower.name(), borrower.emailAddress());
    }
}
