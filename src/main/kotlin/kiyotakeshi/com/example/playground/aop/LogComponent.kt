package kiyotakeshi.com.example.playground.aop

import org.springframework.stereotype.Component

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Component
annotation class LogComponent
