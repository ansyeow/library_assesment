package ansyeow.library.checkout;

import ansyeow.library.checkout.domain.CheckOut;

import java.util.Optional;

public interface CheckOutService {

    void checkOutBook(Long borrowerId, Long bookId);

    void returnBook(Long borrowerId, Long bookId);
}
