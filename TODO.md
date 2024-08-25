# TODO:

- [x] [MDCFilter](src/main/kotlin/kiyotakeshi/com/example/playground/log/mdc/MDCFilter.kt) の polish
  - [x] [TODO: MDCInsertingServletFilter を継承するでもいけそう](https://logback.qos.ch/manual/mdc.html#mis)
    - [SprintBoot としては Logback のみで動作するこの filter に対する機能は提供していない](https://github.com/spring-projects/spring-boot/issues/7927#issuecomment-277008322)
  - [x] OncePerRequestFilter で実装した

- [x] Idiomatic Logging in Kotlin
  - https://www.baeldung.com/kotlin/logging
  - https://tech.askul.co.jp/entry/2019/05/15/124859

- [x] AOP
  - [x] [Logging with AOP](https://www.baeldung.com/spring-aspect-oriented-programming-logging) 
  - [ ] AOP と MDC の組み合わせ
  - [ ] [独自アノテーションを目標に AOP](https://tech.excite.co.jp/entry/2023/12/06/100456)
  - [ ] ドキュメント通読
    - https://docs.spring.io/spring-framework/reference/core/aop.html
    - https://spring.pleiades.io/spring-framework/reference/core/aop.html

- [ ] CGLib proxy について試してみる
  - https://www.baeldung.com/cglib
  - https://github.com/eugenp/tutorials/tree/master/libraries/src/test/java/com/baeldung/cglib/proxy


- [ ] Datadog との連携の確認

- [ ] kotlin-result の導入
