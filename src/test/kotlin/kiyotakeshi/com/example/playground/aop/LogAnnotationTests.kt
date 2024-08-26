package kiyotakeshi.com.example.playground.aop

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class LogAnnotationTests {

    @Autowired
    private lateinit var sampleLogComponent: LogComponentAnnotationSample

    @Autowired
    private lateinit var sampleLogMethod: LogMethodAnnotationSample

    @Test
    fun logComponent() {
        sampleLogComponent.sayHello("mike")
    }

    @Test
    fun logMethod() {
        sampleLogMethod.sayHello("mike")
    }
}
