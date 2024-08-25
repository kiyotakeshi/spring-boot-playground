 # AOP (Aspect Oriented Programming)

- AOP
  - 複数のクラスに点在する横断的な関心事を中心に設計や実装を行うプログラミング手法
  - 特定の横断的な処理をコードに組み込むための手法

## terminology

- Aspect
  - AOP の単位となる横断的な関心事(cross-cutting-concerns)を表すクラスやモジュール
    - ex.) logging, transaction, exception handling, security, caching retry, performance monitoring
- Joinpoint
  - 横断的な関心事(Aspect)を実行するプログラムの特定のポイント、メソッドの実行時や例外の発生など

- Advice
  - Joinpoint に対して実行される横断的な処理
    - ex.) メソッドの前後にログを挿入する

- Pointcut
  - Advice を適用する Joinpoint を指定するための式
    - ex.) 特定のパッケージ内のすべてのメソッド

- Weaving
  - アプリケーションコードの適切なポイントに Aspect を入れ込む処理のこと
  - AOP ライブラリには Weaving をコンパイル時に行うもの、クラスロード時に行うもの、実行時に行うものがある
    - Spring AOP は実行時に Weaving を行う

- Target
  - AOP 処理によって処理フローが変更されたオブジェクトのこと
  - Adviced object とよばれることも

## knowledge

- よくある AOP
  - メソッドの実行前にログを記録する
  - 特定のメソッドが呼び出される前後にトランザクションを開始または終了する
  - 特定の例外が発生した場合に通知を送信する

- AOP の内部の仕組み
  - DI コンテナに登録されている Bean のメソッドを呼ぶ
  - Proxy が自動生成され、Proxy 経由で Bean のメソッドを呼ぶ
  - Bean のメソッドを呼び出す前後に、Advice が実行される

- Spring AOP
  - DI コンテナに管理されている Bean を Target として Advice を埋め込むことができる
  - Advice が適用された場合、DI コンテナに登録されている Bean は Proxy によってラップされた状態になる
  - AOP フレームワークとして AspectJ を利用している
    - AspectJ はコンパイル時、クラスロード時、実行時の Weaving をサポート
    - Spring AOP は Proxy オブジェクトを利用した実行時の Weaving をサポートすることでコンパイルやクラスロードのための設定は不要

- Spring プロジェクトで利用されている AOP
  - @Transactional
  - @PreAuthorize
  - @Cacheable
  - @Async
  - @Retryable

---
## Spring Boot と AOP

https://docs.spring.io/spring-boot/reference/features/aop.html

CGLib proxy をいうものを使っているそう。

> By default, Spring Boot’s auto-configuration configures Spring AOP to use CGLib proxies.
> To use JDK proxies instead, set configprop:spring.aop.proxy-target-class to false.

AspectJ が有効になるので `@EnableAspectJAutoProxy` は不要。 

> If AspectJ is on the classpath,
> Spring Boot’s auto-configuration will automatically enable AspectJ auto proxy such that @EnableAspectJAutoProxy is not required.


`spring-boot-starter-aop` を依存関係に追加

```shell
$ ./gradlew :dependencies

+--- org.springframework.boot:spring-boot-starter-aop -> 3.3.2
|    +--- org.springframework.boot:spring-boot-starter:3.3.2 (*)
|    +--- org.springframework:spring-aop:6.1.11 (*)
|    \--- org.aspectj:aspectjweaver:1.9.22.1
```





