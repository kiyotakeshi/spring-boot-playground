package kiyotakeshi.com.example.playground.log.mdc

import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/mdc/logging")
class MDCController {

    var logger = LoggerFactory.getLogger(MDCController::class.java)

    private fun doSomething() {
        // thread local な値として指定しているので MDC.put で設定した値は適用され続ける
        logger.info("a mdc info message in doSomething")
        logger.warn("a mdc warn message in doSomething")
        // 2024/08/20 09:54:45.733 [http-nio-28080-exec-2] INFO  kiyotakeshi.com.example.playground.log.mdc.MDCController [index] - a mdc info message in doSomething
        // 2024/08/20 09:54:45.733 [http-nio-28080-exec-2] WARN  kiyotakeshi.com.example.playground.log.mdc.MDCController [index] - a mdc warn message in doSomething
    }
    private fun errorSomething() {
        // MDC.remove で取り除いているので [] として表示されている
        logger.error("a mdc error message in errorSomething")
        // 2024/08/20 09:54:45.733 [http-nio-28080-exec-2] ERROR kiyotakeshi.com.example.playground.log.mdc.MDCController [] - a mdc error message in errorSomething
    }

    @GetMapping
    fun index(): String {
        // MDC = Mapped Diagnostic Context(診断コンテキスト)
        // org.slf4j.MDC の static method
        // thread local な値として設定されるので、その thread 内の全てのログ出力箇所に影響する
        // 以下の layout で設定した場合、 [%X{action}] の箇所に出力される
        // <pattern>%d{yyyy/MM/dd HH:mm:ss.SSS} [%thread] %-5level %logger{100} [%X{action}] - %msg%n</pattern>
        MDC.put("action", "index")

        logger.info("a mdc info message in index")
        doSomething()
        logger.warn("a mdc warn message in index")

        // 出力が不要になったため key を指定して取り除く
        MDC.remove("action")
        // MDC 内のデータを全て空にするなら
        // MDC.clear()
        errorSomething()
        return "check out the mdc logs to see the output..."
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