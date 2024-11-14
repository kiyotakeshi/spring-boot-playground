package kiyotakeshi.com.example.playground.transactionPlayground

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Suppress("MagicNumber")
@Service
class MyServiceA(
    private val jdbc: JdbcTemplate
) {
    // default
    @Transactional(propagation = Propagation.REQUIRED)
    fun required() {
        jdbc.update("UPDATE person SET age=? WHERE name = 'Andy'", 21)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun requiresNew() {
        jdbc.update("UPDATE person SET age=? WHERE name = 'Andy'", 21)
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    fun supports() {
        jdbc.update("UPDATE person SET age=? WHERE name = 'Andy'", 21)
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    fun notSupported() {
        jdbc.update("UPDATE person SET age=? WHERE name = 'Andy'", 21)
    }

    @Transactional(propagation = Propagation.MANDATORY)
    fun mandatory() {
        jdbc.update("UPDATE person SET age=? WHERE name = 'Andy'", 21)
    }

    @Transactional(propagation = Propagation.NESTED)
    fun nested() {
        jdbc.update("UPDATE person SET age=? WHERE name = 'Andy'", 21)
    }

    @Transactional(propagation = Propagation.NEVER)
    fun never() {
        jdbc.update("UPDATE person SET age=? WHERE name = 'Andy'", 21)
    }

    fun initDB() {
        jdbc.execute("DROP TABLE IF EXISTS person")
        jdbc.execute(
            "CREATE TABLE person (id SERIAL PRIMARY KEY, name VARCHAR(256), age INTEGER)"
        )
        jdbc.update("INSERT INTO person (name, age) VALUES (?, ?)", "Andy", 19)
    }
}
