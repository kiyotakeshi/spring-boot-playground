## cglib

https://www.baeldung.com/cglib
https://github.com/eugenp/tutorials/tree/master/libraries/src/test/java/com/baeldung/cglib/proxy

いったんは Spring のクラスがどう呼び出しているかを確認して、 必要であれば掘り下げて単体で動作確認をする。

---
> It is a byte instrumentation library used in many Java frameworks such as Hibernate or Spring
> The bytecode instrumentation allows manipulating or creating classes after the compilation phase of a program

コンパイル後のプログラムのクラスを操作したり作成するためのライブラリ = byte instrumentation library

> Classes in Java are loaded dynamically at runtime.
> Cglib is using this feature of Java language to make it possible to add new classes to an already running Java program.

Java のクラスは実行時に動的にロードされるという特性を使って Cglib はすでに実行されている Java のプログラムに新しいクラスを追加する

> Hibernate uses cglib for generation of dynamic proxies. For example, it will not return full object stored in a database but it will return an instrumented version of stored class that lazily loads values from the database on demand.

動的プロキシの生成に cglib を使用し、 DB に格納された完全なオブジェクトを返すのではなく、
必要に応じて lazy load する instrumented version なクラスを返す。

今は Hibernate は byte-buddy を使ってる。
https://mvnrepository.com/artifact/org.hibernate.orm/hibernate-core/6.6.0.Final

> The mock is an instrumented class where methods are replaced by empty implementations.

モックはメソッドが空の実装に置き換えられた instrumented class である。

Mockito も byte-buddy
https://mvnrepository.com/artifact/org.mockito/mockito-core/5.12.0

---
 
- CGLib は active ではなくて代わりのものがいろいろあるらしい
    - https://stackoverflow.com/a/9823788
    - Byte Buddy この位置付けのライブラリだったのか！

- cglib の開発は終わっているけどまだ Guice や MyBatis で使われている
  - https://mvnrepository.com/artifact/cglib/cglib/3.3.0/usages?p=1

- jackson-databind だと `byte-buddy` に乗り換えたよう
    - https://mvnrepository.com/artifact/cglib/cglib/3.1/usages
    - https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind/2.17.2
  - 3.1 までは AssertJ Core も cglib を使っていたが `byte-buddy` に乗り替わっている 
    - https://mvnrepository.com/artifact/cglib/cglib/3.1/usages
    - https://mvnrepository.com/artifact/org.assertj/assertj-core/3.26.3
