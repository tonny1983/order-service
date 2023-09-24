package cc.tonny.orderservice.adapter.outbound.book.dto;

import java.math.BigDecimal;

public record Book(
        String isbn,
        String title,
        String author,
        BigDecimal price
) {
}
