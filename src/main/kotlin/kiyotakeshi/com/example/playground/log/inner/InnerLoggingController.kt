package kiyotakeshi.com.example.playground.log.inner

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/inner/logging")
class InnerLoggingController {

    var logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun index(): String {
        logger.trace("a inner trace message")
        logger.debug("a inner debug message")

        return "check out the inner logs to see the output..."
    }

    @GetMapping("/exception")
    fun exception(): String {
        try {
            throw RuntimeException("this is sample runtime exception")
        } catch (e: Exception) {
            logger.error("an exception message", e)
        }

        return "check out the inner logs to see the output..."
    }
}