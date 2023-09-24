package cc.tonny.orderservice.domain;

import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;

@Table("orders")
public record Order (
        @Id
        Long id,
        String bookIsbn,
        String bookName,
        BigDecimal bookPrice,
        Integer quantity,
        OrderStatus status,

        @CreatedDate
        Instant createdDate,
        @LastModifiedDate
        Instant lastModifiedDate,
        @Version
        int version
){
    public static Order of(String bookIsbn, String bookName, BigDecimal bookPrice, Integer quantity, OrderStatus status) {
        return new Order(null, bookIsbn, bookName, bookPrice, quantity, status, null, null, 0);
    }
}
