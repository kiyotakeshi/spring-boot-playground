package kiyotakeshi.com.example.playground.rest

import kiyotakeshi.com.example.playground.shared.Customer

interface CustomerRepository {
    fun nextIdentifier(): Long
    fun findAllNPlus1(): List<Customer>
    fun findAll(): List<Customer>
    fun findById(id: Long): Customer?
    fun save(newCustomer: Customer)
    fun delete(id: Long): Boolean
}
