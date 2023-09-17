package ansyeow.library.checkout.domain;

import java.util.Optional;

public interface CheckOutHistoryRepository {
    void store(CheckOutHistory checkOutHistory);
}
