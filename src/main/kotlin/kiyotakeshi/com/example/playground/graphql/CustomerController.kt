package kiyotakeshi.com.example.playground.graphql

import kiyotakeshi.com.example.playground.log.getLogger
import org.dataloader.DataLoader
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller
import java.util.concurrent.CompletableFuture

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

//    // CustomerV1 とかなければ単純に @BatchMapping だけでいい
//    @BatchMapping(typeName = "CustomerV2")
//    fun orders(customers: List<Customer>): Map<Customer, List<Order>> {
//        logger.info("called orders")
//        return customers.associateWith { customer ->
//            orderService.ordersByCustomerName(customer.name).sortedBy { order -> order.id }
//        }
//    }

    // 以下のケースは `@BatchMapping` を使用すれば良いシンプルなケースではある
    // kotlin/kiyotakeshi/com/example/playground/graphql/DataLoaderConfig.kt を使用
    @SchemaMapping(typeName = "CustomerV2")
    fun orders(customer: Customer, loader: DataLoader<Customer, List<Order>>): CompletableFuture<List<Order>> {
        return loader.load(customer)
    }

//    @BatchMapping
//    fun detail(orders: List<Order>, @Argument minPrice: Float): Map<Order, OrderDetail?> {
//        logger.info("called detail")
//        return orders.associateWith { order ->
//            orderService.orderDetailByOrderId(order.id, minPrice)
//        }
//    }

    // kotlin/kiyotakeshi/com/example/playground/graphql/DataLoaderConfig.kt を使用
    @SchemaMapping
    fun detail(
        order: Order,
        @Argument minPrice: Float,
        loader: DataLoader<OrderDetailKey, OrderDetail?>
    ): CompletableFuture<OrderDetail?> {
        return loader.load(OrderDetailKey(order.id, minPrice))
    }

    companion object {
        private val logger = getLogger(javaClass)
    }
}
