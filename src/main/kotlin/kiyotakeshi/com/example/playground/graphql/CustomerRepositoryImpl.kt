package kiyotakeshi.com.example.playground.graphql

import kiyotakeshi.com.example.generated.jooq.tables.records.CustomerRecord
import kiyotakeshi.com.example.generated.jooq.tables.references.CUSTOMER
import kiyotakeshi.com.example.playground.shared.Customer
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository("graphqlCustomerRepository")
class CustomerRepositoryImpl(
    private val context: DSLContext,
) : CustomerRepository {
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

    private fun List<CustomerRecord>.toDomainObject(): List<Customer> =
        this.map {
            Customer(
                id = it.customerId,
                name = it.name,
                age = it.age,
                city = it.city,
                orders = emptyList() // DataLoader で取得
            )
        }

    private fun CustomerRecord.toDomainObject(): Customer =
        Customer(
            id = this.customerId,
            name = this.name,
            age = this.age,
            city = this.city,
            orders = emptyList() // DataLoader で取得
        )
}
