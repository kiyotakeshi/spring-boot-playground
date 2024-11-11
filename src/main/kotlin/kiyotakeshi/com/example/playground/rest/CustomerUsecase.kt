package kiyotakeshi.com.example.playground.rest

import org.springframework.stereotype.Service

@Service
class CustomerUsecase(
    private val customerRepository: CustomerRepository
) {
    fun getCustomers(): List<Customer> {
        return customerRepository.findAll()
    }

    fun addCustomer(request: CustomerAddRequestDto): Customer {
        val newId: Long = customerRepository.nextIdentifier()
        val newCustomer = Customer(
            id = newId,
            firstName = request.firstName,
            lastName = request.lastName,
            email = request.email
        )
        customerRepository.save(newCustomer)

        return newCustomer
    }

    fun deleteCustomer(id: String): Boolean {
        return customerRepository.delete(id.toLong())
    }

    fun getCustomerById(id: String): Customer? {
        return customerRepository.findById(id.toLong())
    }
}
