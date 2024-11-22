package kiyotakeshi.com.example.playground.rest

import kiyotakeshi.com.example.generated.jooq.tables.Order.Companion.ORDER
import kiyotakeshi.com.example.playground.shared.Order
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class OrderRepositoryImpl(
    private val context: DSLContext
): OrderRepository {
    override fun findById(orderId: Long): Order? {
        return context.selectFrom(ORDER)
            .where(ORDER.ORDER_ID.eq(orderId))
            .fetchOne()
            ?.let {
                Order(
                    id = it.orderId,
                    description = it.description
                )
            }
    }

    override fun findByCustomerId(customerId: Long): List<Order> {
        return context.selectFrom(ORDER)
            .where(ORDER.CUSTOMER_ID.eq(customerId))
            .fetch()
            .map {
                Order(
                    id = it.orderId,
                    description = it.description
                )
            }
    }

    override fun findByCustomerIds(customerId: Set<Long>): Map<Long, List<Order>> {
        return context.selectFrom(ORDER)
            .where(ORDER.CUSTOMER_ID.`in`(customerId))
            .fetch()
            .groupBy { it.customerId }
            .mapValues { (_, recordList) ->
                recordList.map {
                    Order(
                        id = it.orderId,
                        description = it.description
                    )
                }
            }
    }
}
