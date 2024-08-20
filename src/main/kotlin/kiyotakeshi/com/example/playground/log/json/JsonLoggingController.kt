package kiyotakeshi.com.example.playground.log.json

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/json/logging")
class JsonLoggingController {

    var logger = LoggerFactory.getLogger(JsonLoggingController::class.java)

    @GetMapping
    fun index(): String {
        logger.trace("a json trace message")
        logger.debug("a json debug message")

        return "check out the json logs to see the output..."
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