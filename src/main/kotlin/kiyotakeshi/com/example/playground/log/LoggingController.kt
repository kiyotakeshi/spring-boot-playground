package kiyotakeshi.com.example.playground.log

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/logging")
class LoggingController {

    var logger = LoggerFactory.getLogger(LoggingController::class.java)

    @GetMapping
    fun index(): String {
        logger.trace("a trace message")
        logger.debug("a debug message")
        logger.info("an info message")
        logger.warn("a warn message")
        logger.error("an error message")

        return "check out the logs to see the output..."
    }

}