package kiyotakeshi.com.example.playground.rest

import kiyotakeshi.com.example.generated.jooq.tables.records.CustomerRecord
import kiyotakeshi.com.example.generated.jooq.tables.references.CUSTOMER
import kiyotakeshi.com.example.playground.shared.Customer
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import kotlin.random.Random

@Repository
class CustomerRepositoryImpl(
    private val context: DSLContext,
    private val orderRepository: OrderRepository,
) : CustomerRepository {
    @Suppress("MagicNumber")
    override fun nextIdentifier(): Long {
        return Random.nextLong(1_000L, 10_000L)
    }

    override fun findAllNPlus1(): List<Customer> {
        return context.selectFrom(CUSTOMER)
            .fetch()
            .toList()
            .toDomainObjectNPlus1()
    }

    override fun findAll(): List<Customer> {
        return context.selectFrom(CUSTOMER)
            .fetch()
            .toList()
            .toDomainObject()
    }

    override fun findById(id: Long): Customer? {
        return context.selectFrom(CUSTOMER)
            .where(CUSTOMER.CUSTOMER_ID.eq(id))
            .fetchOne()?.toDomainObject()
    }

    override fun save(newCustomer: Customer) {
        context.newRecord(CUSTOMER).apply {
            customerId = newCustomer.id
            name = newCustomer.name
            age = newCustomer.age
            city = newCustomer.city
        }.merge()
    }

    override fun delete(id: Long): Boolean {
        val affectRows = context.deleteFrom(CUSTOMER)
            .where(CUSTOMER.CUSTOMER_ID.eq(id))
            .execute()
        return affectRows > 0
    }

    private fun List<CustomerRecord>.toDomainObjectNPlus1(): List<Customer> =
        this.map {
            Customer(
                id = it.customerId,
                name = it.name,
                age = it.age,
                city = it.city,
                orders = orderRepository.findByCustomerId(it.customerId)
            )
        }

    // N+1 を解消
    private fun List<CustomerRecord>.toDomainObject(): List<Customer> {
        val customerIds = this.map { it.customerId }.toSet()
        val orders = orderRepository.findByCustomerIds(customerIds)
        return this.map {
            Customer(
                id = it.customerId,
                name = it.name,
                age = it.age,
                city = it.city,
                orders = orders[it.customerId] ?: emptyList()
            )
        }
    }

    private fun CustomerRecord.toDomainObject(): Customer =
        Customer(
            id = this.customerId,
            name = this.name,
            age = this.age,
            city = this.city,
            orders = orderRepository.findByCustomerId(this.customerId)
        )
}
