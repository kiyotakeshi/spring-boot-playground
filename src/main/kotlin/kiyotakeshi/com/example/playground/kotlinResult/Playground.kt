package kiyotakeshi.com.example.playground.kotlinResult

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result

object Error

data class Order(val id: Int, val name: String)

data class User(val id: Int, val name: String, val token: String? = null, val orders: Set<Order>) {
    fun hasManyOrders(): Result<Unit, Error> {
        println("hasManyOrders: $this")
        if (this.orders.size < 2) {
            return Err(Error)
        }
        return Ok(Unit)
    }
}

@Suppress("MaxLineLength")
fun getUser(id: Int): Result<User, Error> {
    println("getUser: $id")
    val users = listOf(
        User(id = 1, name = "user1", token = "asdfghjkll", orders = setOf(Order(id = 1, name = "order1"), Order(id = 2, name = "order2"))),
        User(id = 2, name = "user2", orders = emptySet()),
        User(id = 3, name = "user2", token = "lkjhgfdsa", orders = setOf(Order(id = 2, name = "order2"))),

    )
    users.find { it.id == id }?.let {
        return Ok(it)
    } ?: return Err(Error)
}

fun login(user: User): Result<Unit, Error> {
    println("login: $user")
    user.token?.let {
        return Ok(Unit)
    } ?: return Err(Error)
}
