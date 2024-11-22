package kiyotakeshi.com.example.playground.graphql

import kiyotakeshi.com.example.playground.shared.Customer
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class CustomerService(
    @Qualifier("graphqlCustomerRepository")
    private val customerRepository: CustomerRepository
) {

    @Suppress("MagicNumber")
    fun allCustomers(): List<Customer> {
        return customerRepository.findAll()
    }

    @Suppress("MagicNumber")
    fun customer(id: Long): Customer? {
        return customerRepository.findById(id)
    }
}
