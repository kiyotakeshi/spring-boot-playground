package kiyotakeshi.com.example.playground.transactionPlayground

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Suppress("TooManyFunctions")
@RestController
@RequestMapping("/transaction")
class TransactionPlaygroundController(
    private val myServiceA: MyServiceA,
    private val myServiceB: MyServiceB
) {
    @GetMapping("/required")
    fun required() {
        myServiceA.initDB()
        myServiceA.required()
    }

    @GetMapping("/required_nested")
    fun requiredNested() {
        myServiceB.initDB()
        myServiceB.required()
    }

    @GetMapping("/requires_new")
    fun requiresNew() {
        myServiceA.initDB()
        myServiceA.requiresNew()
    }

    @GetMapping("/requires_new_nested")
    fun requiresNewNested() {
        myServiceB.initDB()
        myServiceB.requiresNew()
    }

    @GetMapping("/supports")
    fun supports() {
        myServiceA.initDB()
        myServiceA.supports()
    }

    @GetMapping("/supports_nested")
    fun supportsNested() {
        myServiceB.initDB()
        myServiceB.supports()
    }

    @GetMapping("/not_supported")
    fun notSupported() {
        myServiceA.initDB()
        myServiceA.notSupported()
    }

    @GetMapping("/not_supported_nested")
    fun notSupportedNested() {
        myServiceB.initDB()
        myServiceB.notSupported()
    }

    @GetMapping("/mandatory")
    fun mandatory() {
        myServiceA.initDB()
        myServiceA.mandatory()
    }

    @GetMapping("/mandatory_nested")
    fun mandatoryNested() {
        myServiceB.initDB()
        myServiceB.mandatory()
    }

    @GetMapping("/nested")
    fun nested() {
        myServiceA.initDB()
        myServiceA.nested()
    }

    @GetMapping("/nested_nested")
    fun nestedNested() {
        myServiceB.initDB()
        myServiceB.nested()
    }

    @GetMapping("/never")
    fun never() {
        myServiceA.initDB()
        myServiceA.never()
    }

    @GetMapping("/never_nested")
    fun neverNested() {
        myServiceB.initDB()
        myServiceB.never()
    }
}
