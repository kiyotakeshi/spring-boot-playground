package kiyotakeshi.com.example.playground.aop

@LogComponent
class LogComponentAnnotationSample {

    fun sayHello(name: String): String {
        return "hello $name"
    }
}
