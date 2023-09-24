package cc.tonny.orderservice.adapter.outbound;

import cc.tonny.orderservice.config.DataConfig;
import cc.tonny.orderservice.config.TestDataConfig;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;

@DataR2dbcTest
@Import({DataConfig.class, TestDataConfig.class})
public class OrderRepositoryEmbeddedTests extends OrderRepositoryTests {
}
