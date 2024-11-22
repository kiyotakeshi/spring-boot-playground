package kiyotakeshi.com.example.playground.graphql

import kiyotakeshi.com.example.playground.log.getLogger
import kiyotakeshi.com.example.playground.shared.Order
import kiyotakeshi.com.example.playground.shared.OrderDetail
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class OrderService(
    @Qualifier("graphqlOrderRepository")
    private val orderRepository: OrderRepository
) {
    fun ordersByCustomerIds(customerIds: Set<Long>): Map<Long, List<Order>> {
        logger.info("called ordersByCustomerIds")
        return orderRepository.findByCustomerIds(customerIds)
    }

    companion object {
        private val logger = getLogger(javaClass)
    }
}
