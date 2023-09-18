package ansyeow.library.borrower.domain;

import ansyeow.library.borrower.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BorrowerServiceImpl implements BorrowerService {
    @Autowired
    private BorrowerRepository borrowerRepository;

    @Override
    public Borrower registerNewBorrower(String name, String emailAddress) {
        final Borrower newBorrower =
                new Borrower(null, name, emailAddress);
        return borrowerRepository.store(newBorrower);
    }

    @Override
    public Optional<Borrower> findById(Long id) {
        return borrowerRepository.findById(id);
    }
}
