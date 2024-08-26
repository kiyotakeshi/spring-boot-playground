package kiyotakeshi.com.example.playground.aop

import org.springframework.stereotype.Component

@Component
class LogMethodAnnotationSample {

    @LogMethod
    fun sayHello(name: String): String {
        return "hello $name"
    }
}
