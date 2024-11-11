package kiyotakeshi.com.example.playground.kotlinResult

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.flatMap
import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapBoth
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.onSuccess
import com.github.michaelbull.result.orElse
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
        // receiver をそのまま返すため = user.onSuccessの戻り値は Result 型であり、内部で login 関数を呼び出してもその結果は外部に返されない
        // public inline infix fun <V, E> Result<V, E>.onSuccess(action: (V) -> Unit): Result<V, E> {
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

    /**
     * https://blog.nnn.dev/entry/2023/06/22/110000#kotlin-result%E3%81%AE%E5%9F%BA%E6%9C%AC%E3%83%A1%E3%82%BD%E3%83%83%E3%83%89
     * にある基本的なメソッドの確認
     */
    @Test
    fun `kotlin-result の基本的なメソッド確認`() {
        val user: User? = getUser(1).get()
        assertThat(user).isInstanceOf(User::class.java)

        val userError = getUser(1).getError()
        assertThat(userError).isNull()

        val user1000 = getUser(1000).get()
        assertThat(user1000).isNull()

        val user1000Error = getUser(1000).getError()
        assertThat(user1000Error).isInstanceOf(Error::class.java)

        assertThat(getUser(1)
            // 引数のラムダで変換する
            .map { "success" }).isEqualTo(Ok("success"))

        assertThat(getUser(1000).mapError { "error" }).isEqualTo(Err("error"))

        val andThenOrElse: Result<String, String> = getUser(1000)
            .andThen { Ok("success") }
            .orElse { Err("error") }

        assertThat(andThenOrElse).isEqualTo(Err("error"))

        val flatMap: Result<String, String> = getUser(1000)
            .flatMap { Ok("success") }
            .orElse { Err("error") }

        println(flatMap)
    }
}
