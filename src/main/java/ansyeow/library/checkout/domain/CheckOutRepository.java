package ansyeow.library.checkout.domain;

import java.util.Optional;

public interface CheckOutRepository {

    Optional<CheckOut> findByBookId(Long bookId);
    void store(CheckOut checkOut);
    void discard(CheckOut checkOut);
}
