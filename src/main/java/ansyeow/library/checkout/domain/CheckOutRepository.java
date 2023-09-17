package ansyeow.library.checkout.domain;

import ansyeow.library.book.domain.Book;
import ansyeow.library.borrower.domain.Borrower;

import java.util.Optional;

public interface CheckOutRepository {

    Optional<CheckOut> findByBook(Book book, Optional<Borrower> borrowerOpt);

    void store(CheckOut checkOut);
    void discard(CheckOut checkOut);
}
