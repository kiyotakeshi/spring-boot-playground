package kiyotakeshi.com.example.playground.log

open class LoggerAsExtensionOnAny {
    val logger = logger()

    fun log(s: String) {
        logger().info(s)
        logger.info(s)
    }
}

class ExtensionSubclass : LoggerAsExtensionOnAny()

// fun main() {
//    // 23:18:30.840 [main] INFO kiyotakeshi.com.example.playground.log.LoggerAsExtensionOnAny -- test
//    // 23:18:30.841 [main] INFO kiyotakeshi.com.example.playground.log.LoggerAsExtensionOnAny -- test
//    LoggerAsExtensionOnAny().log("test")
//
//    // 23:18:30.841 [main] INFO kiyotakeshi.com.example.playground.log.ExtensionSubclass -- sub
//    // 23:18:30.841 [main] INFO kiyotakeshi.com.example.playground.log.ExtensionSubclass -- sub
//    ExtensionSubclass().log("sub")
//
//    // 23:29:33.734 [main] INFO java.lang.String -- Any type pollution
//    "foo".logger().info("Any type pollution")
// }
