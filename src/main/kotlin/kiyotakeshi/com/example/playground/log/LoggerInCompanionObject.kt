package kiyotakeshi.com.example.playground.log

import kiyotakeshi.com.example.playground.log.LoggerInCompanionObject.Companion.loggerEnclosingClass
import kiyotakeshi.com.example.playground.log.LoggerInCompanionObject.Companion.loggerWithExplicitClass
import kiyotakeshi.com.example.playground.log.LoggerInCompanionObject.Companion.loggerWithWrongClass

/**
 * companion object 内で logger を定義する場合のただしい Class 名の取得のための検証
 */
open class LoggerInCompanionObject {
    fun log(s: String) {
        loggerWithExplicitClass.info(s)
        loggerWithWrongClass.info(s)
        loggerEnclosingClass.info(s)
    }

    companion object {
        val loggerWithExplicitClass = getLogger(LoggerInCompanionObject::class.java)

        // LoggerInCompanionObject$Companion が取得される
        @Suppress("JAVA_CLASS_ON_COMPANION")
        val loggerWithWrongClass = getLogger(javaClass)

        // enclosingClass は外部クラスを取得するため LoggerInCompanionObject が取得される
        @Suppress("JAVA_CLASS_ON_COMPANION")
        val loggerEnclosingClass = getLogger(javaClass.enclosingClass)
    }

    @Suppress("UtilityClassWithPublicConstructor")
    class Inner {
        companion object {
            val loggerWithExplicitClass = getLogger(Inner::class.java)

            @Suppress("JAVA_CLASS_ON_COMPANION")
            @JvmStatic
            val loggerWithWrongClass = getLogger(javaClass)

            // enclosingClass は外部クラスを取得するため LoggerInCompanionObject$Inner が取得される
            @Suppress("JAVA_CLASS_ON_COMPANION")
            @JvmStatic
            val loggerEnclosingClass = getLogger(javaClass.enclosingClass)
        }
    }
}

class CompanionSubclass : LoggerInCompanionObject()

// .info() などのメソッドは void のため UT ではなく main function で動作確認する
// fun main() {
//    // tier1()
//    // tier2()
//
//    // 23:06:36.371 [main] INFO kiyotakeshi.com.example.playground.log.LoggerInCompanionObject -- test
//    // 23:06:36.372 [main] INFO kiyotakeshi.com.example.playground.log.LoggerInCompanionObject$Companion -- test
//    // 23:06:36.372 [main] INFO kiyotakeshi.com.example.playground.log.LoggerInCompanionObject -- test
//    CompanionSubclass().log("test")
// }

@Suppress("UnusedPrivateMember")
private fun tier1() {
    // redundant qualifier name
    // LoggerInCompanionObject.loggerWithExplicitClass.info("test")
    // 22:54:51.635 [main] INFO kiyotakeshi.com.example.playground.log.LoggerInCompanionObject -- test
    loggerWithExplicitClass.info("test")

    // 22:54:51.636 [main] INFO kiyotakeshi.com.example.playground.log.LoggerInCompanionObject$Companion -- test
    loggerWithWrongClass.info("test")

    // 22:54:51.635 [main] INFO kiyotakeshi.com.example.playground.log.LoggerInCompanionObject -- test
    loggerEnclosingClass.info("test")
}

@Suppress("UnusedPrivateMember")
private fun tier2() {
    // 22:59:23.373 [main] INFO kiyotakeshi.com.example.playground.log.LoggerInCompanionObject$Inner -- test
    LoggerInCompanionObject.Inner.loggerWithExplicitClass.info("test")

    // 23:02:44.743 [main] INFO kiyotakeshi.com.example.playground.log.LoggerInCompanionObject$Inner$Companion -- test
    LoggerInCompanionObject.Inner.loggerWithWrongClass.info("test")

    // 23:03:51.700 [main] INFO kiyotakeshi.com.example.playground.log.LoggerInCompanionObject$Inner -- test
    LoggerInCompanionObject.Inner.loggerEnclosingClass.info("test")
}
