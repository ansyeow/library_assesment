package ansyeow.library.borrower;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


@SpringBootTest
public class BorrowerIntegrationTest {
    @Test
    public void givenValidBody_whenRegisterNewBorrower_thenReceiveNewBorrowerId() {
        given().body("""
                        {"name":"Andrew", "emailAddress":"andrew@yahoo.com"}
                        """).
        when(). post("/borrower").
        then(). statusCode(201).
                body("borrower.id", equalTo(1234));
    }
}
