package kiyotakeshi.com.example.playground.aop

import org.springframework.stereotype.Service

@Service
class Sample {

    fun sayHello(name: String): String {
        return "hello $name"
    }

    fun findUser(name: String): Boolean {
        println("find user by $name")
        return true
    }
}
