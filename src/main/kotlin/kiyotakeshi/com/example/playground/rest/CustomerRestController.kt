package kiyotakeshi.com.example.playground.rest

import com.github.michaelbull.result.getOrThrow
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import kiyotakeshi.com.example.playground.shared.Customer
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/customers")
class CustomerRestController(
    private val customerUsecase: CustomerUsecase,
) {

    @GetMapping
    fun getCustomers(): List<Customer> {
        return customerUsecase.getCustomers()
    }


    @GetMapping("/{id}")
    fun getCustomer(@PathVariable id: String): ResponseEntity<Customer> {
        val customer = customerUsecase.getCustomerById(id).getOrThrow()
        return ResponseEntity.ok(customer)
    }

    @GetMapping("/v2/{id}")
    fun getCustomerV2(@PathVariable id: String): Customer {
        return customerUsecase.getCustomerById2(id)
    }

    @PostMapping
    fun add(
        @RequestBody @Valid request: CustomerAddRequestDto,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<Void> {
        val newCustomer: Customer = customerUsecase.addCustomer(request)

        val locationUri = uriBuilder
            .path("/tasks/{id}")
            .buildAndExpand(newCustomer.id)
            .toUri()

        return ResponseEntity.created(locationUri).build()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Void> =
        if (customerUsecase.deleteCustomer(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
}

data class CustomerAddRequestDto(
    @get:Size(min = 1, max = 30)
    val name: String,

    @get:NotNull
    val age: Int,

    @get:Size(min = 1, max = 255)
    val city: String,
)
