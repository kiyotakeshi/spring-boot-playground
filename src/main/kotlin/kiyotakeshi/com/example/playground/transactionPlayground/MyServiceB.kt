package kiyotakeshi.com.example.playground.transactionPlayground

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Suppress("MaxLineLength", "MagicNumber")
@Service
class MyServiceB(
    private val jdbc: JdbcTemplate,
    private val myServiceA: MyServiceA
) {
    // default
    @Transactional(propagation = Propagation.REQUIRED)
    fun required() {
        // 2024/11/14 17:57:08.256 [http-nio-28080-exec-5] DEBUG org.springframework.jdbc.support.JdbcTransactionManager - Creating new transaction with name [kiyotakeshi.com.example.playground.transactionPlayground.MyServiceB.required]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
        // Switching JDBC Connection [HikariProxyConnection@52095809 wrapping org.postgresql.jdbc.PgConnection@1510d100] to manual commit
        // 2024/11/14 17:57:08.256 [http-nio-28080-exec-5] DEBUG org.postgresql.jdbc.PgConnection -   setAutoCommit = false
        jdbc.update("UPDATE person SET age=? WHERE name = 'Bobby'", 20)
        myServiceA.required()
    }

    @Transactional(propagation = Propagation.REQUIRED)
    fun requiresNew() {
        // 2024/11/14 18:07:58.091 [http-nio-28080-exec-8] DEBUG org.springframework.jdbc.support.JdbcTransactionManager - Creating new transaction with name [kiyotakeshi.com.example.playground.transactionPlayground.MyServiceB.requiresNew]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
        // 2024/11/14 18:07:58.091 [http-nio-28080-exec-8] DEBUG org.springframework.jdbc.support.JdbcTransactionManager - Switching JDBC Connection [HikariProxyConnection@491423435 wrapping org.postgresql.jdbc.PgConnection@1510d100] to manual commit
        // 2024/11/14 18:07:58.091 [http-nio-28080-exec-8] DEBUG org.postgresql.jdbc.PgConnection -   setAutoCommit = false
        // 2024/11/14 18:07:58.092 [http-nio-28080-exec-8] DEBUG org.springframework.jdbc.support.JdbcTransactionManager - Suspending current transaction, creating new transaction with name [kiyotakeshi.com.example.playground.transactionPlayground.MyServiceA.requiresNew]
        // 2024/11/14 18:07:58.093 [http-nio-28080-exec-8] DEBUG org.springframework.jdbc.support.JdbcTransactionManager - Switching JDBC Connection [HikariProxyConnection@369921078 wrapping org.postgresql.jdbc.PgConnection@5f392ce2] to manual commit
        // 2024/11/14 18:07:58.093 [http-nio-28080-exec-8] DEBUG org.postgresql.jdbc.PgConnection -   setAutoCommit = false
        // 2024/11/14 18:07:58.095 [http-nio-28080-exec-8] DEBUG org.springframework.jdbc.support.JdbcTransactionManager - Initiating transaction commit
        // 2024/11/14 18:07:58.095 [http-nio-28080-exec-8] DEBUG org.springframework.jdbc.support.JdbcTransactionManager - Committing JDBC transaction on Connection [HikariProxyConnection@369921078 wrapping org.postgresql.jdbc.PgConnection@5f392ce2]
        // 2024/11/14 18:07:58.096 [http-nio-28080-exec-8] DEBUG org.springframework.jdbc.support.JdbcTransactionManager - Releasing JDBC Connection [HikariProxyConnection@369921078 wrapping org.postgresql.jdbc.PgConnection@5f392ce2] after transaction
        // 2024/11/14 18:07:58.096 [http-nio-28080-exec-8] DEBUG org.springframework.jdbc.support.JdbcTransactionManager - Resuming suspended transaction after completion of inner transaction
        // 2024/11/14 18:07:58.096 [http-nio-28080-exec-8] DEBUG org.springframework.jdbc.support.JdbcTransactionManager - Initiating transaction commit
        // 2024/11/14 18:07:58.097 [http-nio-28080-exec-8] DEBUG org.springframework.jdbc.support.JdbcTransactionManager - Committing JDBC transaction on Connection [HikariProxyConnection@491423435 wrapping org.postgresql.jdbc.PgConnection@1510d100]
        jdbc.update("UPDATE person SET age=? WHERE name = 'Bobby'", 20)
        myServiceA.requiresNew()
    }

    @Transactional(propagation = Propagation.REQUIRED)
    fun supports() {
        jdbc.update("UPDATE person SET age=? WHERE name = 'Bobby'", 20)
        myServiceA.supports()
    }

    @Transactional(propagation = Propagation.REQUIRED)
    fun notSupported() {
        jdbc.update("UPDATE person SET age=? WHERE name = 'Bobby'", 20)
        myServiceA.notSupported()
    }

    @Transactional(propagation = Propagation.REQUIRED)
    fun mandatory() {
        jdbc.update("UPDATE person SET age=? WHERE name = 'Bobby'", 20)
        myServiceA.mandatory()
    }

    @Transactional(propagation = Propagation.REQUIRED)
    fun nested() {
        jdbc.update("UPDATE person SET age=? WHERE name = 'Bobby'", 20)
        myServiceA.nested()
    }

    @Transactional(propagation = Propagation.REQUIRED)
    fun never() {
        jdbc.update("UPDATE person SET age=? WHERE name = 'Bobby'", 20)
        myServiceA.never()
    }

    fun initDB() {
        jdbc.execute("DROP TABLE IF EXISTS person")
        jdbc.execute(
            "CREATE TABLE person (id SERIAL PRIMARY KEY, name VARCHAR(256), age INTEGER)"
        )
        jdbc.update("INSERT INTO person (name, age) VALUES (?, ?)", "Bobby", 19)
    }
}
