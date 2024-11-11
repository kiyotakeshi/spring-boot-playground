package kiyotakeshi.com.example.playground.rest

interface CustomerRepository {
    fun nextIdentifier(): Long
    fun findAll(): List<Customer>
    fun findById(id: Long): Customer?
    fun save(newCustomer: Customer)
    fun delete(id: Long): Boolean
}
