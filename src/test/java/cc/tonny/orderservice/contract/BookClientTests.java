package cc.tonny.orderservice.contract;

import cc.tonny.orderservice.adapter.outbound.book.BookClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureJsonTesters
@AutoConfigureStubRunner(
        stubsMode = StubRunnerProperties.StubsMode.LOCAL,
        ids = "cc.tonny:catalog-service:+:stubs:0.0.1:8080"
)
public class BookClientTests {
    @Autowired
    private BookClient bookClient;

    @Test
    void whenBookExistedThenReturnIt() {
        var isbn = "1234567890";
        var result = bookClient.getBookByIsbn(isbn);

        StepVerifier.create(result)
                .assertNext(book -> assertThat(book.isbn()).isEqualTo(isbn)).verifyComplete();

    }

    @Test
    void whenBookNotExistedThenReturnEmpty() {
        var isbn = "0987654321";
        var result = bookClient.getBookByIsbn(isbn);

        StepVerifier.create(result)
                .verifyComplete();
    }
}
