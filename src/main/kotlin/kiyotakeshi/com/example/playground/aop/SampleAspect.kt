@file:Suppress("EmptyFunctionBlock")

package kiyotakeshi.com.example.playground.aop

// 間違えた package を import しないように注意
// import org.aopalliance.intercept.Joinpoint

import kiyotakeshi.com.example.playground.log.LoggerAsExtensionOnMarkerInterface.Companion.logger
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class SampleAspect {

//    @Pointcut("within(@org.springframework.stereotype.Service *)")
//    fun service() {
//    }

    @Pointcut("execution(* kiyotakeshi.com.example.playground.aop.Sample.find*(..)))")
    fun findMethod() {
    }

    @Pointcut("execution(public * kiyotakeshi.com.example.playground.aop.*.*(..))")
    fun aopPackage() {
    }

//    @Before("service()")
//    fun startLog(joinpoint: JoinPoint) {
//        logger.info("startLog ${joinpoint.signature.declaringTypeName}.${joinpoint.signature.name}")
//    }

    @Before("findMethod()")
    fun findLog(joinpoint: JoinPoint) {
        logger.info("findLog ${joinpoint.signature.declaringTypeName}.${joinpoint.signature.name} - ${joinpoint.args.contentToString()}")
    }

    @AfterReturning(pointcut = "aopPackage()", returning = "result")
    fun logAfter(joinPoint: JoinPoint, result: Any?) {
        logger.info("logAfter << {$joinPoint.signature.name}() - $result")
    }

    @Around(value = "aopPackage()")
    @Throws(Throwable::class)
    fun logAround(joinPoint: ProceedingJoinPoint): Any {
        val methodName = joinPoint.signature.name
        logger.info("logAround >> $methodName() - ${joinPoint.args.contentToString()}")
        val result = joinPoint.proceed()
        logger.info("logAround << $methodName() - $result")
        return result
    }

    @Around("execution(* kiyotakeshi.com.example.playground.aop.AOPController.*(..))")
    @Throws(Throwable::class)
    fun logExecutionTime(joinPoint: ProceedingJoinPoint): Any? {
        val t = System.currentTimeMillis()
        val result = joinPoint.proceed()
        logger.info("Execution time=" + (System.currentTimeMillis() - t) + "ms")
        return result
    }

    companion object {
        var logger = LoggerFactory.getLogger(javaClass)
    }
}
