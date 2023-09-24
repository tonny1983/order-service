package cc.tonny.orderservice.adapter.outbound;

import cc.tonny.orderservice.adapter.outbound.book.dto.Book;
import cc.tonny.orderservice.domain.OrderService;
import cc.tonny.orderservice.domain.OrderStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

@Disabled("This is a base test which should never be run!")
public class OrderRepositoryTests {
    @Autowired
    private OrderRepository orderRepository;

    @Test
    void createRejectedOrder() {
        var rejectedOrder = OrderService.buildRejectedOrder("1234567890", 2);
        StepVerifier.create(orderRepository.save(rejectedOrder))
                .expectNextMatches(
                        order -> order.status().equals(OrderStatus.REJECTED)
                ).verifyComplete();
    }

    @Test
    void createAcceptedOrder() {
        var book = new Book("0987654321", "title", "author", BigDecimal.ONE);
        var acceptedOrder = OrderService.buildAcceptedOrder(book, 3);
        StepVerifier.create(orderRepository.save(acceptedOrder))
                .expectNextMatches(
                        order -> order.status().equals(OrderStatus.ACCEPTED)
                ).verifyComplete();
    }
}
