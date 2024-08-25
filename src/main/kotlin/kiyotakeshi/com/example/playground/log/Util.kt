package kiyotakeshi.com.example.playground.log

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.full.companionObject

fun <T : Any> getClassForLogging(javaClass: Class<T>): Class<*> {
    // javaClass が内部クラスである場合、その外部クラスを返し、そうでない場合は null を返す(動きの確認については LoggerInCompanionObject.kt を参照)
    // `?.takeIf { ... }` は、`?.` なので enclosingClass が null でない場合にのみブロック内の条件を評価する
    return javaClass.enclosingClass?.takeIf {
        // enclosingClass がコンパニオンオブジェクトを持ち、そのコンパニオンオブジェクトのクラスが引数の javaClass と同じであるかをチェック
        // 条件が真の場合、takeIf は enclosingClass を返し、偽の場合は null を返す
        // takeIf が null を返す場合は `?: javaClass` のため、javaClass が返される
        // つまり、javaClass がコンパニオンオブジェクトの場合、その外部クラスを返す
        it.kotlin.companionObject?.java == javaClass
    } ?: javaClass
}

fun getLogger(forClass: Class<*>): Logger = LoggerFactory.getLogger(forClass)

// Pollution of the Any Type になってしまう
fun <T : Any> T.logger(): Logger = getLogger(getClassForLogging(javaClass))

fun main() {
    // javaClass.enclosingClass が null なので、そのまま引数の javaClass が返される
    val regularClass = getClassForLogging(String::class.java)
    // Regular class: java.lang.String
    println("Regular class: ${regularClass.name}")

    // javaClass.enclosingClass が null なので、そのまま引数の javaClass が返される
    val classWithCompanion = getClassForLogging(LoggerInCompanionObject::class.java)
    // Class with companion object: kiyotakeshi.com.example.playground.log.LoggerInCompanionObject
    println("Class with companion object: ${classWithCompanion.name}")

    // javaClass.enclosingClass が class kiyotakeshi.com.example.playground.log.LoggerInCompanionObject
    // it.kotlin.companionObject は class kiyotakeshi.com.example.playground.log.LoggerInCompanionObject$Companion = null じゃない
    // it.kotlin.companionObject.java も class kiyotakeshi.com.example.playground.log.LoggerInCompanionObject$Companion
    // javaClass も class kiyotakeshi.com.example.playground.log.LoggerInCompanionObject$Companion
    // javaClass と同じであるため、takeIf 内の `it.kotlin.companionObject?.java == javaClass` は true のため enclosingClass を返す
    val companionClass = getClassForLogging(LoggerInCompanionObject.Companion::class.java)
    // Companion object class: kiyotakeshi.com.example.playground.log.LoggerInCompanionObject
    println("Companion object class: ${companionClass.name}")

    // javaClass は class kiyotakeshi.com.example.playground.log.LoggerInCompanionObject$Inner
    // enclosingClass は class kiyotakeshi.com.example.playground.log.LoggerInCompanionObject
    // it.kotlin.companionObject.java は class kiyotakeshi.com.example.playground.log.LoggerInCompanionObject$Companion なので
    // it.kotlin.companionObject?.java == javaClass は false となり、 takeIf は null を返すため ?: javaClass が返される
    // つまり class kiyotakeshi.com.example.playground.log.LoggerInCompanionObject$Inner が返される
    val innerClass = getClassForLogging(LoggerInCompanionObject.Inner::class.java)
    // Inner class: kiyotakeshi.com.example.playground.log.LoggerInCompanionObject$Inner
    println("Inner class: ${innerClass.name}")

    // javaClass は class kiyotakeshi.com.example.playground.log.LoggerInCompanionObject$Inner$Companion
    // enclosingClass は class kiyotakeshi.com.example.playground.log.LoggerInCompanionObject$Inner
    // it.kotlin.companionObject.java は class kiyotakeshi.com.example.playground.log.LoggerInCompanionObject$Inner$Companion なので
    // it.kotlin.companionObject?.java == javaClass は true となり、 takeIf は enclosingClass を返す
    val innerClassCompanionClass = getClassForLogging(LoggerInCompanionObject.Inner.Companion::class.java)
    // Inner class companion object class: kiyotakeshi.com.example.playground.log.LoggerInCompanionObject$Inner
    println("Inner class companion object class: ${innerClassCompanionClass.name}")
}
