package ansyeow.library.borrower;

import ansyeow.library.borrower.domain.Borrower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BorrowerController {

    @Autowired
    private BorrowerService borrowerService;

    @PostMapping("/borrower")
    Borrower registerNewBorrower(@RequestBody Borrower newBorrower) {
        return borrowerService.registerNewBorrower(newBorrower.name(), newBorrower.emailAddress());
    }
}