package kiyotakeshi.com.example.playground.rest

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kiyotakeshi.com.example.playground.shared.Customer
import org.springframework.stereotype.Service

@Service
class CustomerUsecase(
    private val customerRepository: CustomerRepository
) {
    fun getCustomers(): List<Customer> {
        return customerRepository.findAllNPlus1()
    }

    fun getCustomerById(id: String): Result<Customer, CustomerException> {
        customerRepository.findById(id.toLong())?.let {
            return Ok(it)
        } ?: return Err(CustomerNotFound())
    }

    fun getCustomerById2(id: String): Customer {
        return customerRepository.findById(id.toLong()) ?: throw CustomerNotFound()
    }

    fun addCustomer(request: CustomerAddRequestDto): Customer {
        val newId: Long = customerRepository.nextIdentifier()
        val newCustomer = Customer(
            id = newId,
            name = request.name,
            age = request.age,
            city = request.city
        )
        customerRepository.save(newCustomer)

        return newCustomer
    }

    fun deleteCustomer(id: String): Boolean {
        return customerRepository.delete(id.toLong())
    }
}

sealed class CustomerException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)

class CustomerNotFound : CustomerException("Customer not found")
