package kiyotakeshi.com.example.playground.graphql

import kiyotakeshi.com.example.playground.shared.Order

interface OrderRepository {
    fun findById(orderId: Long): Order?
    fun findByCustomerIds(customerId: Set<Long>): Map<Long, List<Order>>
}
