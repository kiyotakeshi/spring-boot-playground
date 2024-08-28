package kiyotakeshi.com.example.playground.graphql

import kiyotakeshi.com.example.playground.log.getLogger
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.BatchMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class CustomerController(
    private val customerService: CustomerService,
    private val orderService: OrderService
) {
    // ----------------------------
    // for N+1
    @QueryMapping
    fun customersNPlus1(): List<Customer> {
        logger.info("called customers")
        return customerService.allCustomers().sortedBy { it.id }
    }

    @QueryMapping
    fun customerNPlus1(@Argument id: Long): Customer? {
        logger.info("called customer")
        return customerService.allCustomers().firstOrNull { it.id == id }
    }

    // @SchemaMapping(typeName = "CustomerV1", field = "ordersNPlus1")
    @SchemaMapping(typeName = "CustomerV1")
    fun ordersNPlus1(customer: Customer): List<Order> {
        // N+1 occurs
        logger.info("called orders")
        return orderService.ordersByCustomerName(customer.name).sortedBy { it.id }
    }

    // ----------------------------
    // for DataLoader
    @QueryMapping
    fun customers(): List<Customer> {
        logger.info("called customers")
        return customerService.allCustomers().sortedBy { it.id }
    }

    @QueryMapping
    fun customer(@Argument id: Long): Customer? {
        logger.info("called customer")
        return customerService.allCustomers().firstOrNull { it.id == id }
    }

    // CustomerV1 とかなければ単純に @BatchMapping だけでいい
    @BatchMapping(typeName = "CustomerV2")
    fun orders(customers: List<Customer>): Map<Customer, List<Order>> {
        logger.info("called orders")
        return customers.associateWith { customer ->
            orderService.ordersByCustomerName(customer.name).sortedBy { order -> order.id }
        }
    }

    @BatchMapping
    fun detail(orders: List<Order>): Map<Order, OrderDetail?> {
        logger.info("called detail")
        return orders.associateWith { order ->
            orderService.orderDetailByOrderId(order.id)
        }
    }

    companion object {
        private val logger = getLogger(javaClass)
    }
}
