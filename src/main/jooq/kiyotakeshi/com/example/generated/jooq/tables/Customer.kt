/*
 * This file is generated by jOOQ.
 */
package kiyotakeshi.com.example.generated.jooq.tables


import kiyotakeshi.com.example.generated.jooq.Playground
import kiyotakeshi.com.example.generated.jooq.keys.CUSTOMER_EMAIL_KEY
import kiyotakeshi.com.example.generated.jooq.keys.CUSTOMER_PKEY
import kiyotakeshi.com.example.generated.jooq.tables.records.CustomerRecord

import kotlin.collections.Collection
import kotlin.collections.List

import org.jooq.Condition
import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.InverseForeignKey
import org.jooq.Name
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
import org.jooq.impl.SQLDataType
import org.jooq.impl.TableImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class Customer(
    alias: Name,
    path: Table<out Record>?,
    childPath: ForeignKey<out Record, CustomerRecord>?,
    parentPath: InverseForeignKey<out Record, CustomerRecord>?,
    aliased: Table<CustomerRecord>?,
    parameters: Array<Field<*>?>?,
    where: Condition?
): TableImpl<CustomerRecord>(
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
         * The reference instance of <code>playground.customer</code>
         */
        val CUSTOMER: Customer = Customer()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<CustomerRecord> = CustomerRecord::class.java

    /**
     * The column <code>playground.customer.customer_id</code>.
     */
    val CUSTOMER_ID: TableField<CustomerRecord, Long?> = createField(DSL.name("customer_id"), SQLDataType.BIGINT.nullable(false), this, "")

    /**
     * The column <code>playground.customer.first_name</code>.
     */
    val FIRST_NAME: TableField<CustomerRecord, String?> = createField(DSL.name("first_name"), SQLDataType.VARCHAR(255).nullable(false), this, "")

    /**
     * The column <code>playground.customer.last_name</code>.
     */
    val LAST_NAME: TableField<CustomerRecord, String?> = createField(DSL.name("last_name"), SQLDataType.VARCHAR(255).nullable(false), this, "")

    /**
     * The column <code>playground.customer.email</code>.
     */
    val EMAIL: TableField<CustomerRecord, String?> = createField(DSL.name("email"), SQLDataType.VARCHAR(255).nullable(false), this, "")

    private constructor(alias: Name, aliased: Table<CustomerRecord>?): this(alias, null, null, null, aliased, null, null)
    private constructor(alias: Name, aliased: Table<CustomerRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, null, aliased, parameters, null)
    private constructor(alias: Name, aliased: Table<CustomerRecord>?, where: Condition?): this(alias, null, null, null, aliased, null, where)

    /**
     * Create an aliased <code>playground.customer</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>playground.customer</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>playground.customer</code> table reference
     */
    constructor(): this(DSL.name("customer"), null)
    override fun getSchema(): Schema? = if (aliased()) null else Playground.PLAYGROUND
    override fun getPrimaryKey(): UniqueKey<CustomerRecord> = CUSTOMER_PKEY
    override fun getUniqueKeys(): List<UniqueKey<CustomerRecord>> = listOf(CUSTOMER_EMAIL_KEY)
    override fun `as`(alias: String): Customer = Customer(DSL.name(alias), this)
    override fun `as`(alias: Name): Customer = Customer(alias, this)
    override fun `as`(alias: Table<*>): Customer = Customer(alias.qualifiedName, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): Customer = Customer(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): Customer = Customer(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): Customer = Customer(name.qualifiedName, null)

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Condition?): Customer = Customer(qualifiedName, if (aliased()) this else null, condition)

    /**
     * Create an inline derived table from this table
     */
    override fun where(conditions: Collection<Condition>): Customer = where(DSL.and(conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(vararg conditions: Condition?): Customer = where(DSL.and(*conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Field<Boolean?>?): Customer = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(condition: SQL): Customer = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String): Customer = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg binds: Any?): Customer = where(DSL.condition(condition, *binds))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg parts: QueryPart): Customer = where(DSL.condition(condition, *parts))

    /**
     * Create an inline derived table from this table
     */
    override fun whereExists(select: Select<*>): Customer = where(DSL.exists(select))

    /**
     * Create an inline derived table from this table
     */
    override fun whereNotExists(select: Select<*>): Customer = where(DSL.notExists(select))
}
