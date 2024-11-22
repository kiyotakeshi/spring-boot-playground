package kiyotakeshi.com.example.playground.graphql

import kiyotakeshi.com.example.playground.log.getLogger
import kiyotakeshi.com.example.playground.shared.Customer
import kiyotakeshi.com.example.playground.shared.Order
import kiyotakeshi.com.example.playground.shared.OrderDetail
import org.dataloader.DataLoader
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.BatchMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller
import java.util.concurrent.CompletableFuture

@Controller
class CustomerController(
    private val customerService: CustomerService,
    private val orderService: OrderService
) {
    @QueryMapping
    fun customers(): List<Customer> {
        logger.info("called customers")
        return customerService.allCustomers().sortedBy { it.id }
    }

    @QueryMapping
    fun customer(@Argument id: Long): Customer? {
        logger.info("called customer")
        return customerService.customer(id)
    }

    @BatchMapping
    fun orders(customers: List<Customer>): Map<Customer, List<Order>> {
        logger.info("called orders")
        val customerIdToOrders = orderService.ordersByCustomerIds(customers.map { it.id }.toSet())
        return customers
            .associateWith { customer ->
            customerIdToOrders[customer.id] ?: emptyList()
        }
    }

    // 以下のケースは `@BatchMapping` を使用すれば良いシンプルなケースではある
    // kotlin/kiyotakeshi/com/example/playground/graphql/DataLoaderConfig.kt を使用
//    @SchemaMapping(typeName = "Customer")
//    fun orders(customer: Customer, loader: DataLoader<Customer, List<Order>>): CompletableFuture<List<Order>> {
//        return loader.load(customer)
//    }

//    @BatchMapping
//    fun detail(orders: List<Order>, @Argument minPrice: Float): Map<Order, OrderDetail?> {
//        logger.info("called detail")
//        return orders.associateWith { order ->
//            orderService.orderDetailByOrderId(order.id, minPrice)
//        }
//    }

    // kotlin/kiyotakeshi/com/example/playground/graphql/DataLoaderConfig.kt を使用
//    @SchemaMapping
//    fun detail(
//        order: Order,
//        @Argument minPrice: Float,
//        loader: DataLoader<OrderDetailKey, OrderDetail?>
//    ): CompletableFuture<OrderDetail?> {
//        return loader.load(OrderDetailKey(order.id, minPrice))
//    }

    companion object {
        private val logger = getLogger(javaClass)
    }
}
