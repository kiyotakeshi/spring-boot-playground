package kiyotakeshi.com.example.playground.graphql

import org.springframework.stereotype.Service

@Service
class CustomerService {

    @Suppress("MagicNumber")
    fun allCustomers() = listOf(
        Customer(1, "mike", 30, "New York"),
        Customer(2, "john", 25, "San Francisco"),
        Customer(3, "kanye", 40, "Chicago"),
    )
}

data class Customer(
    val id: Long,
    val name: String,
    val age: Int,
    val city: String,
    val orders: List<Order> = emptyList()
)
