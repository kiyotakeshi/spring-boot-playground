/*
 * This file is generated by jOOQ.
 */
package kiyotakeshi.com.example.generated.jooq.tables.records


import kiyotakeshi.com.example.generated.jooq.tables.OrderDetail

import org.jooq.impl.TableRecordImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class OrderDetailRecord private constructor() : TableRecordImpl<OrderDetailRecord>(OrderDetail.ORDER_DETAIL) {

    open var orderId: Long
        set(value): Unit = set(0, value)
        get(): Long = get(0) as Long

    open var productCode: String
        set(value): Unit = set(1, value)
        get(): String = get(1) as String

    open var quantity: Int
        set(value): Unit = set(2, value)
        get(): Int = get(2) as Int

    open var price: Double
        set(value): Unit = set(3, value)
        get(): Double = get(3) as Double

    /**
     * Create a detached, initialised OrderDetailRecord
     */
    constructor(orderId: Long, productCode: String, quantity: Int, price: Double): this() {
        this.orderId = orderId
        this.productCode = productCode
        this.quantity = quantity
        this.price = price
        resetChangedOnNotNull()
    }
}
