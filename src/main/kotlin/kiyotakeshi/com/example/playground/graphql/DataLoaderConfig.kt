package kiyotakeshi.com.example.playground.graphql

import org.springframework.context.annotation.Configuration
import org.springframework.graphql.execution.BatchLoaderRegistry
import reactor.core.publisher.Mono

@Configuration
class DataLoaderConfig(
    batchLoaderRegistry: BatchLoaderRegistry,
    private val orderService: OrderService
) {
    init {
        // https://docs.spring.io/spring-graphql/reference/request-execution.html#execution.batching.batch-loader-registry
        // @SchemaMapping で argument を受け取らない時の使い方(`@BatchMapping` で代用可能なもの)
        batchLoaderRegistry
            .forTypePair(
                Customer::class.java,
                List::class.java
            )
            .registerMappedBatchLoader { key, _ ->
                Mono.just(
                    key.associateWith { customer ->
                        orderService.ordersByCustomerName(customer.name).sortedBy { order -> order.id }
                    }
                )
            }
        // argument を扱うための定義
        // https://github.com/spring-projects/spring-graphql/issues/417#issuecomment-1159441888
        batchLoaderRegistry
            .forTypePair(
                OrderDetailKey::class.java,
                OrderDetail::class.java
            )
            .registerMappedBatchLoader { keys, _ ->
                Mono.just(
                    keys.associateWith { key ->
                        orderService.orderDetailByOrderId(key.orderId, key.minPrice)
                    }
                )
            }
    }
}

data class OrderDetailKey(val orderId: Long, val minPrice: Float)
