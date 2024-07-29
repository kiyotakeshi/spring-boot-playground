package kiyotakeshi.com.example.demo_profile

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
    @Value("\${message.hello}")
    private val hello: String? = null

    @Value("\${message.goodbye}")
    private val goodbye: String? = null

    @Value("\${server.port}")
    private val port: String? = null

    @GetMapping("/hello")
    fun hello(): String? {
        return hello
    }

    @GetMapping("/goodbye")
    fun goodbye(): String? {
        return goodbye
    }

    @GetMapping("/port")
    fun port(): String? {
        return port
    }
}