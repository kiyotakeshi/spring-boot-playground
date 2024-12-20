/*
 * This file is generated by jOOQ.
 */
package kiyotakeshi.com.example.generated.jooq.tables.records


import kiyotakeshi.com.example.generated.jooq.tables.Customer

import org.jooq.Record1
import org.jooq.impl.UpdatableRecordImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class CustomerRecord private constructor() : UpdatableRecordImpl<CustomerRecord>(Customer.CUSTOMER) {

    open var customerId: Long
        set(value): Unit = set(0, value)
        get(): Long = get(0) as Long

    open var name: String
        set(value): Unit = set(1, value)
        get(): String = get(1) as String

    open var age: Int
        set(value): Unit = set(2, value)
        get(): Int = get(2) as Int

    open var city: String
        set(value): Unit = set(3, value)
        get(): String = get(3) as String

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<Long?> = super.key() as Record1<Long?>

    /**
     * Create a detached, initialised CustomerRecord
     */
    constructor(customerId: Long, name: String, age: Int, city: String): this() {
        this.customerId = customerId
        this.name = name
        this.age = age
        this.city = city
        resetChangedOnNotNull()
    }
}
