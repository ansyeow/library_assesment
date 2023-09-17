package ansyeow.library.checkout.domain;


import java.time.ZonedDateTime;

public record CheckOutHistory(CheckOut checkOut,
                              ZonedDateTime returnTime) {
}