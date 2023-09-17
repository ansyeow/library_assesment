package ansyeow.library.checkout.domain;

import ansyeow.library.book.BookService;
import ansyeow.library.book.domain.Book;
import ansyeow.library.borrower.BorrowerService;
import ansyeow.library.borrower.domain.Borrower;
import ansyeow.library.checkout.CheckOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class CheckOutServiceImpl implements CheckOutService {
    @Autowired
    private BorrowerService borrowerService;
    @Autowired
    private BookService bookService;
    @Autowired
    private CheckOutRepository checkOutRepository;
    @Autowired
    private CheckOutHistoryRepository checkOutHistoryRepository;

    public void checkOutBook(Long borrowerId, Long bookId) {
        Optional<Borrower> borrowerOpt = borrowerService.findById(borrowerId);
        Assert.isTrue(borrowerOpt.isPresent(), "Borrower not found. borrowerId: " + borrowerId);

        Optional<Book> bookOpt = bookService.findById(bookId);
        Assert.isTrue(bookOpt.isPresent(), "Book not found. bookId: " + bookId);

        final Optional<CheckOut> checkOutOpt =
                checkOutRepository.findByBook(bookOpt.get(), borrowerOpt);
        Assert.isTrue(checkOutOpt.isEmpty(), "Book already checked-out. checkOut: " + checkOutOpt.get());

        final CheckOut newCheckOut =
                new CheckOut(borrowerOpt.get(), bookOpt.get(), ZonedDateTime.now());
        checkOutRepository.store(newCheckOut);
    }

    public void returnBook(Long borrowerId, Long bookId) {
        Optional<Borrower> borrowerOpt = borrowerService.findById(borrowerId);
        Assert.isTrue(borrowerOpt.isPresent(), "Borrower not found. borrowerId: " + borrowerId);

        Optional<Book> bookOpt = bookService.findById(bookId);
        Assert.isTrue(bookOpt.isPresent(), "Book not found. bookId: " + bookId);

        final Optional<CheckOut> checkOutOpt =
            checkOutRepository.findByBook(bookOpt.get(), borrowerOpt);
        Assert.isTrue(checkOutOpt.isPresent(), "Check-out not found");

        final Long checkOutBorrowerId = checkOutOpt.get().borrower().id();
        Assert.isTrue(checkOutBorrowerId.equals(borrowerId),
                String.format("Check-out borrowerId[%d] not match return borrowerId[%d]",
                        checkOutBorrowerId, borrowerId));

        checkOutRepository.discard(checkOutOpt.get());

        final CheckOutHistory newCheckOutHistory =
                new CheckOutHistory(checkOutOpt.get(), ZonedDateTime.now());
        checkOutHistoryRepository.store(newCheckOutHistory);
    }
}
