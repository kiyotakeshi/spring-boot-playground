package kiyotakeshi.com.example.playground.log

import org.slf4j.Logger
import org.slf4j.LoggerFactory

// marker interface
interface Logging

// avoid pollution of the Any Type
fun <T : Logging> T.logger(): Logger =
    LoggerFactory.getLogger(getClassForLogging(javaClass).name + " Logging/interface")
    // LoggerFactory.getLogger(getClassForLogging(T::class.java).name + " Logging/interface")


open class LoggerAsExtensionOnMarkerInterface : Logging {
    companion object : Logging {
        val logger = logger()
    }

    fun log(s: String) {
        logger().info(s)
        logger.info(s)
    }
}

class MarkerExtensionSubclass : LoggerAsExtensionOnMarkerInterface()

fun main() {
    // 00:22:45.781 [main] INFO kiyotakeshi.com.example.playground.log.LoggerAsExtensionOnMarkerInterface Logging/interface -- test
    // 00:22:45.782 [main] INFO kiyotakeshi.com.example.playground.log.LoggerAsExtensionOnMarkerInterface Logging/interface -- test
    LoggerAsExtensionOnMarkerInterface().log("test")

    // 00:22:45.782 [main] INFO kiyotakeshi.com.example.playground.log.MarkerExtensionSubclass Logging/interface -- sub
    // 00:22:45.782 [main] INFO kiyotakeshi.com.example.playground.log.LoggerAsExtensionOnMarkerInterface Logging/interface -- sub
    MarkerExtensionSubclass().log("sub")

    // 00:25:06.687 [main] INFO java.lang.String -- Any type pollution
    "foo".logger().info("Any type pollution")
}

