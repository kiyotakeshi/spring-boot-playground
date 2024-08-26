package kiyotakeshi.com.example.playground.aop

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/aop")
class AOPController(
    private val sample: Sample,
    private val logMethodAnnotationSample: LogMethodAnnotationSample
) {
    @GetMapping("/{name}")
    fun index(@PathVariable name: String): String {
        Thread.sleep(DELAY_SEC)
        return sample.sayHello(name)
    }

    @GetMapping("/users/{name}")
    fun user(@PathVariable name: String): String {
        return logMethodAnnotationSample.findUser(name)
    }

    companion object {
        private const val DELAY_SEC = 1_000L
    }
}
