package kiyotakeshi.com.example.playground.graphql

import kiyotakeshi.com.example.playground.log.getLogger
import org.springframework.stereotype.Service

@Service
class OrderService {

    fun ordersByCustomerName(name: String): List<Order> {
        logger.info("fetch orders by customer name: $name")
        return ORDER_MAP[name] ?: emptyList()
    }

    fun orderDetailByOrderId(orderId: Long): OrderDetail? {
        logger.info("fetch order details by order id: $orderId")
        return ORDER_DETAIL_MAP[orderId]
    }

    companion object {
        private val ORDER_MAP = mapOf(
            "mike" to listOf(Order(1, "beer"), Order(2, "book")),
            "john" to listOf(Order(3, "computer")),
        )

        private val ORDER_DETAIL_MAP = mapOf(
            1L to OrderDetail(1, "001", 1, 100.0f),
            2L to OrderDetail(2, "002", 2, 200.0f),
            3L to OrderDetail(3, "003", 1, 300.0f),
        )
        private val logger = getLogger(javaClass)
    }
}

data class Order(
    val id: Long,
    val description: String
)

data class OrderDetail(
    val id: Long,
    val productCode: String,
    val quantity: Int,
    val price: Float
)
