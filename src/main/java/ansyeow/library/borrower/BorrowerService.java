package ansyeow.library.borrower;

import ansyeow.library.borrower.domain.Borrower;

import java.util.Optional;

public interface BorrowerService {

    Borrower registerNewBorrower(String name, String emailAddress);

    Optional<Borrower> findById(Long id);
}
