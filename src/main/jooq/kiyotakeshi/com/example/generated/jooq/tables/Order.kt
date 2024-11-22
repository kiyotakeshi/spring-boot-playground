/*
 * This file is generated by jOOQ.
 */
package kiyotakeshi.com.example.generated.jooq.tables


import kiyotakeshi.com.example.generated.jooq.Playground
import kiyotakeshi.com.example.generated.jooq.keys.ORDER_DETAIL__ORDER_DETAIL_ORDER_ID_FKEY
import kiyotakeshi.com.example.generated.jooq.keys.ORDER_PKEY
import kiyotakeshi.com.example.generated.jooq.keys.ORDER__ORDER_CUSTOMER_ID_FKEY
import kiyotakeshi.com.example.generated.jooq.tables.Customer.CustomerPath
import kiyotakeshi.com.example.generated.jooq.tables.OrderDetail.OrderDetailPath
import kiyotakeshi.com.example.generated.jooq.tables.records.OrderRecord

import kotlin.collections.Collection
import kotlin.collections.List

import org.jooq.Condition
import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.InverseForeignKey
import org.jooq.Name
import org.jooq.Path
import org.jooq.PlainSQL
import org.jooq.QueryPart
import org.jooq.Record
import org.jooq.SQL
import org.jooq.Schema
import org.jooq.Select
import org.jooq.Stringly
import org.jooq.Table
import org.jooq.TableField
import org.jooq.TableOptions
import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType
import org.jooq.impl.TableImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class Order(
    alias: Name,
    path: Table<out Record>?,
    childPath: ForeignKey<out Record, OrderRecord>?,
    parentPath: InverseForeignKey<out Record, OrderRecord>?,
    aliased: Table<OrderRecord>?,
    parameters: Array<Field<*>?>?,
    where: Condition?
): TableImpl<OrderRecord>(
    alias,
    Playground.PLAYGROUND,
    path,
    childPath,
    parentPath,
    aliased,
    parameters,
    DSL.comment(""),
    TableOptions.table(),
    where,
) {
    companion object {

        /**
         * The reference instance of <code>playground.order</code>
         */
        val ORDER: Order = Order()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<OrderRecord> = OrderRecord::class.java

    /**
     * The column <code>playground.order.order_id</code>.
     */
    val ORDER_ID: TableField<OrderRecord, Long?> = createField(DSL.name("order_id"), SQLDataType.BIGINT.nullable(false), this, "")

    /**
     * The column <code>playground.order.customer_id</code>.
     */
    val CUSTOMER_ID: TableField<OrderRecord, Long?> = createField(DSL.name("customer_id"), SQLDataType.BIGINT.nullable(false), this, "")

    /**
     * The column <code>playground.order.description</code>.
     */
    val DESCRIPTION: TableField<OrderRecord, String?> = createField(DSL.name("description"), SQLDataType.VARCHAR(255).nullable(false), this, "")

    private constructor(alias: Name, aliased: Table<OrderRecord>?): this(alias, null, null, null, aliased, null, null)
    private constructor(alias: Name, aliased: Table<OrderRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, null, aliased, parameters, null)
    private constructor(alias: Name, aliased: Table<OrderRecord>?, where: Condition?): this(alias, null, null, null, aliased, null, where)

    /**
     * Create an aliased <code>playground.order</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>playground.order</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>playground.order</code> table reference
     */
    constructor(): this(DSL.name("order"), null)

    constructor(path: Table<out Record>, childPath: ForeignKey<out Record, OrderRecord>?, parentPath: InverseForeignKey<out Record, OrderRecord>?): this(Internal.createPathAlias(path, childPath, parentPath), path, childPath, parentPath, ORDER, null, null)

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    open class OrderPath : Order, Path<OrderRecord> {
        constructor(path: Table<out Record>, childPath: ForeignKey<out Record, OrderRecord>?, parentPath: InverseForeignKey<out Record, OrderRecord>?): super(path, childPath, parentPath)
        private constructor(alias: Name, aliased: Table<OrderRecord>): super(alias, aliased)
        override fun `as`(alias: String): OrderPath = OrderPath(DSL.name(alias), this)
        override fun `as`(alias: Name): OrderPath = OrderPath(alias, this)
        override fun `as`(alias: Table<*>): OrderPath = OrderPath(alias.qualifiedName, this)
    }
    override fun getSchema(): Schema? = if (aliased()) null else Playground.PLAYGROUND
    override fun getPrimaryKey(): UniqueKey<OrderRecord> = ORDER_PKEY
    override fun getReferences(): List<ForeignKey<OrderRecord, *>> = listOf(ORDER__ORDER_CUSTOMER_ID_FKEY)

    private lateinit var _customer: CustomerPath

    /**
     * Get the implicit join path to the <code>playground.customer</code> table.
     */
    fun customer(): CustomerPath {
        if (!this::_customer.isInitialized)
            _customer = CustomerPath(this, ORDER__ORDER_CUSTOMER_ID_FKEY, null)

        return _customer;
    }

    val customer: CustomerPath
        get(): CustomerPath = customer()

    private lateinit var _orderDetail: OrderDetailPath

    /**
     * Get the implicit to-many join path to the
     * <code>playground.order_detail</code> table
     */
    fun orderDetail(): OrderDetailPath {
        if (!this::_orderDetail.isInitialized)
            _orderDetail = OrderDetailPath(this, null, ORDER_DETAIL__ORDER_DETAIL_ORDER_ID_FKEY.inverseKey)

        return _orderDetail;
    }

    val orderDetail: OrderDetailPath
        get(): OrderDetailPath = orderDetail()
    override fun `as`(alias: String): Order = Order(DSL.name(alias), this)
    override fun `as`(alias: Name): Order = Order(alias, this)
    override fun `as`(alias: Table<*>): Order = Order(alias.qualifiedName, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): Order = Order(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): Order = Order(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): Order = Order(name.qualifiedName, null)

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Condition?): Order = Order(qualifiedName, if (aliased()) this else null, condition)

    /**
     * Create an inline derived table from this table
     */
    override fun where(conditions: Collection<Condition>): Order = where(DSL.and(conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(vararg conditions: Condition?): Order = where(DSL.and(*conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Field<Boolean?>?): Order = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(condition: SQL): Order = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String): Order = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg binds: Any?): Order = where(DSL.condition(condition, *binds))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg parts: QueryPart): Order = where(DSL.condition(condition, *parts))

    /**
     * Create an inline derived table from this table
     */
    override fun whereExists(select: Select<*>): Order = where(DSL.exists(select))

    /**
     * Create an inline derived table from this table
     */
    override fun whereNotExists(select: Select<*>): Order = where(DSL.notExists(select))
}