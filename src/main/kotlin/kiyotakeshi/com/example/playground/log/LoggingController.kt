package kiyotakeshi.com.example.playground.log

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/logging")
class LoggingController {

    // package 名を string で指定したらログにもそのように出力された
    // Class object で渡した方が IDE を使った rename にも強いし良さそう
//    var logger = LoggerFactory.getLogger("kiyotakeshi.com.example.playground.log")
//    2024/08/19 21:20:38 INFO  k.com.example.playground.log - an info message
//    2024/08/19 21:20:38 WARN  k.com.example.playground.log - a warn message
//    2024/08/19 21:20:38 ERROR k.com.example.playground.log - an error message

//    var logger = LoggerFactory.getLogger(LoggingController::class.java)
//    2024/08/19 21:21:28 INFO  k.c.e.p.log.LoggingController - an info message
//    2024/08/19 21:21:28 WARN  k.c.e.p.log.LoggingController - a warn message
//    2024/08/19 21:21:28 ERROR k.c.e.p.log.LoggingController - an error message

    // property として logger を宣言 = javaClass で取得できる
    var logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun index(): String {
        // LoggerFactory に渡す logger.trace を logging context という
        logger.trace("a trace message")
        logger.debug("a debug message")
        logger.info("an info message")
        logger.warn("a warn message")
        logger.error("an error message")

        return "check out the logs to see the output..."
    }
}
