package kiyotakeshi.com.example.playground.aop

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SampleTests {

    @Autowired
    lateinit var sample: Sample

    @Test
    fun sayHello() {
        println(sample.sayHello("mike"))
        // AOP により差し込まれたログが呼ばれる
    }

    @Test
    fun findUser() {
        println(sample.findUser("mike"))
    }
}