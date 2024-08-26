package kiyotakeshi.com.example.playground.aop

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class LogMethod
