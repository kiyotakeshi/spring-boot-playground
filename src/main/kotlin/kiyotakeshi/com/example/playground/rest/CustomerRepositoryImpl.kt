package kiyotakeshi.com.example.playground.rest

import kiyotakeshi.com.example.generated.jooq.tables.records.CustomerRecord
import kiyotakeshi.com.example.generated.jooq.tables.references.CUSTOMER
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import kotlin.random.Random

@Repository
class CustomerRepositoryImpl(
    private val context: DSLContext,
) : CustomerRepository {
    @Suppress("MagicNumber")
    override fun nextIdentifier(): Long {
        return Random.nextLong(1_000L, 10_000L)
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
            firstName = newCustomer.firstName
            lastName = newCustomer.lastName
            email = newCustomer.email
        }.merge()
    }

    override fun delete(id: Long): Boolean {
        return context.deleteFrom(CUSTOMER)
            .where(CUSTOMER.CUSTOMER_ID.eq(id))
            .execute() == 1
    }

    private fun List<CustomerRecord>.toDomainObject(): List<Customer> =
        this.map {
            Customer(
                id = it.customerId,
                firstName = it.firstName,
                lastName = it.lastName,
                email = it.email
            )
        }

    private fun CustomerRecord.toDomainObject(): Customer =
        Customer(
            id = this.customerId,
            firstName = this.firstName,
            lastName = this.lastName,
            email = this.email
        )
}
