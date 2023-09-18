package ansyeow.library.borrower;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BorrowerIntegrationTest {

    @Value(value="${local.server.port}")
    private int port;

    @Test
    public void givenValidBody_whenRegisterNewBorrower_thenReceiveNewBorrowerId() {
        given() .baseUri("http://localhost:" + port + "/")
                .body("""
                        {"name":"Andrew", "emailAddress":"andrew@yahoo.com"}
                        """).
        when(). post("/borrower").
        then(). statusCode(201).
                body("borrower.id", equalTo(1234));
    }
}
