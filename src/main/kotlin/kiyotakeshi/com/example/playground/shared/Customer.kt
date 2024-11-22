package kiyotakeshi.com.example.playground.shared

data class Customer(
    val id: Long,
    val name: String,
    val age: Int,
    val city: String,
    val orders: List<Order> = emptyList()
)
