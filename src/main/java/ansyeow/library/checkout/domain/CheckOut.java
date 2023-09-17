package ansyeow.library.checkout.domain;


import ansyeow.library.book.domain.Book;
import ansyeow.library.borrower.domain.Borrower;

import java.time.ZonedDateTime;

public record CheckOut(Book book,
                       Borrower borrower,
                       ZonedDateTime checkOutTime) {
}