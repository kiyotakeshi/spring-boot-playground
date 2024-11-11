package kiyotakeshi.com.example.playground.kotlinResult

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.flatMap
import com.github.michaelbull.result.mapBoth
import com.github.michaelbull.result.onSuccess
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * https://note.com/telin/n/n6d9e352c344c のサンプルコードを参考に色々動かしてみたもの
 */
class Playground {

    @Suppress("ExplicitItLambdaParameter")
    @Test
    fun ざっと動かす() {
        val user1 = getUser(1)
            .flatMap { it: User -> login(it) }
            // login の戻り値が Unit なので繋げられない
            // .flatMap { it: Unit -> hasManyOrders() }
            .mapBoth(
                success = { "success" },
                failure = { "failure" }
            )
        assertThat(user1).isEqualTo("success")

        val user1000 = getUser(1000)
            .flatMap { it: User -> login(it) }
            // login の戻り値が Unit なので繋げられない
            // .flatMap { it: Unit -> hasManyOrders() }
            .mapBoth(
                success = { "success" },
                failure = { "failure" }
            )
        assertThat(user1000).isEqualTo("failure")

        assertThat(getUser(2).flatMap { login(it) }.isErr).isTrue()
        assertThat(getUser(3).flatMap { login(it) }.isOk).isTrue()
        assertThat(getUser(3).flatMap { it.hasManyOrders() }.isErr).isTrue()
        getUser(3).flatMap { login(it) }
    }

    @Suppress("ExplicitItLambdaParameter")
    @Test
    fun error_notFound() {
        val user: Result<User, Error> = getUser(4)

        // この時点で Error が帰ってくる
        println(user.isOk) // false
        println(user.isErr) // true
        println(user) // Err(kiyotakeshi.com.example.playground.kotlinResult.Error@1a5b6f42)

        val login: Result<User, Error> = user.onSuccess { it: User ->
            // 呼ばれない
            println("onSuccess: $it")
            login(it)
        }
        // 同じ Error インスタンス なのは一つ下のケース `error_noToken onSuccess の扱いに注意` で理由がわかった
        println(login) // Err(kiyotakeshi.com.example.playground.kotlinResult.Error@1a5b6f42)

        val hasManyOrders = login.onSuccess { it: User ->
            // 呼ばれない
            println("onSuccess: $it")
            it.hasManyOrders()
        }
        // 同じ Error インスタンス
        println(hasManyOrders)

        assertThat(hasManyOrders.isOk).isFalse()
        assertThat(hasManyOrders.isErr).isTrue()
        assertThat(hasManyOrders.error).isEqualTo(Error)
    }

    @Suppress("ExplicitItLambdaParameter")
    @Test
    fun `error_noToken onSuccess の扱いに注意`() {
        val user: Result<User, Error> = getUser(2)
        println(user.isOk) // true
        println(user.isErr) // false
        println(user) // Ok(User(id=2, name=user2, token=null, orders=[]))

        // token は null だが user と同じ変数の中身になる
        // user.onSuccessの戻り値は Result 型であり、内部で login 関数を呼び出してもその結果は外部に返されない
        val login: Result<User, Error> = user.onSuccess {
            // これは呼ばれる
            println("onSuccess: $it")
            login(it)
        }

        println(login) // Ok(User(id=2, name=user2, token=null, orders=[]))

        val hasManyOrders = login.onSuccess { it: User ->
            // これもよばれる
            println("onSuccess in hasManyOrders: $it")
            it.hasManyOrders()
        }
        assertThat(hasManyOrders.isErr).isFalse()
        // onSuccess の結果が代入されるわけではないので、変数の中身は同じ
        assertThat(user).isEqualTo(login)
        assertThat(user).isEqualTo(hasManyOrders)
    }

    @Test
    fun error_noToken() {
        val user: Result<User, Error> = getUser(2)

        val login: Result<Unit, Error> = user.andThen {
            login(it)
        }

        assertThat(login.isOk).isFalse()
        assertThat(login.isErr).isTrue()
        assertThat(login.error).isEqualTo(Error)
    }
}
