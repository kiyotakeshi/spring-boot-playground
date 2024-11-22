package kiyotakeshi.com.example.playground.graphql

import kiyotakeshi.com.example.playground.shared.Customer

interface CustomerRepository {
    fun findAll(): List<Customer>
    fun findById(id: Long): Customer?
}
