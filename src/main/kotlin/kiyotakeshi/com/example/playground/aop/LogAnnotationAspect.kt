package kiyotakeshi.com.example.playground.aop

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class LogAnnotationAspect {

    @Before("@annotation(LogMethod)")
    fun beforeLogMethod(joinPoint: JoinPoint) {
        val path = "${joinPoint.signature.declaringTypeName}.${joinPoint.signature.name}"
        val body = joinPoint.args.contentToString()

        logger.info("beforeLogMethod $path, $body")
    }

    @Before("within(@kiyotakeshi.com.example.playground.aop.LogComponent *)")
    fun beforeLogComponent(joinPoint: JoinPoint) {
        val path = "${joinPoint.signature.declaringTypeName}.${joinPoint.signature.name}"
        val body = joinPoint.args.contentToString()

        logger.info("beforeLogComponent $path, $body")
    }

    companion object {
        var logger = LoggerFactory.getLogger(javaClass)
    }
}
