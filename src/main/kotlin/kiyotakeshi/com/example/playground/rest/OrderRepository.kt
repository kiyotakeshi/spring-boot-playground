package kiyotakeshi.com.example.playground.rest

import kiyotakeshi.com.example.playground.shared.Order

interface OrderRepository {
    fun findById(orderId: Long): Order?
    fun findByCustomerId(customerId: Long): List<Order>
    fun findByCustomerIds(customerId: Set<Long>): Map<Long, List<Order>>
}
