package ansyeow.library.borrower.domain;

import java.util.Optional;

public interface BorrowerRepository {
    Optional<Borrower> findById(Long id);
    Borrower store(Borrower borrower);
}
