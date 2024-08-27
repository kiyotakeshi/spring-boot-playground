package kiyotakeshi.com.example.playground.graphql

import kiyotakeshi.com.example.playground.log.getLogger
import org.springframework.stereotype.Service

@Service
class OrderService {

    fun ordersByCustomerName(name: String): List<Order> {
        logger.info("fetch orders by customer name: $name")
        return ORDER_MAP[name] ?: emptyList()
    }

    companion object {
        private val ORDER_MAP = mapOf(
            "mike" to listOf(Order(1, "beer"), Order(2, "book")),
            "john" to listOf(Order(3, "computer")),
        )
        private val logger = getLogger(javaClass)
    }
}

data class Order(
    val id: Long,
    val description: String
)
