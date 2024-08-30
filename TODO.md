# TODO:

- [x] [MDCFilter](src/main/kotlin/kiyotakeshi/com/example/playground/log/mdc/MDCFilter.kt) の polish
  - [x] [TODO: MDCInsertingServletFilter を継承するでもいけそう](https://logback.qos.ch/manual/mdc.html#mis)
    - [SprintBoot としては Logback のみで動作するこの filter に対する機能は提供していない](https://github.com/spring-projects/spring-boot/issues/7927#issuecomment-277008322)
  - [x] OncePerRequestFilter で実装した

- [x] Idiomatic Logging in Kotlin
  - https://www.baeldung.com/kotlin/logging
  - https://tech.askul.co.jp/entry/2019/05/15/124859

- [x] README の整理

- [x] detekt の導入

- [x] Spring for GraphQL 動作確認
  - [x] N+1 を起こして DataLoader(`@BatchMapping`) にて解消
  - [x] DataLoader そのものについて調べる
  - [x] DataLoader に `@Argument` を受け取る挙動を検証

- [x] CGLib proxy について調べてみる

- [ ] Datadog との連携の確認
  - https://engineer-first.net/3nfno590njal
  - https://docs.datadoghq.com/tracing/guide/tutorial-enable-java-host/?tab=maven
  - https://docs.datadoghq.com/tracing/guide/tutorial-enable-java-containers/
  - https://docs.datadoghq.com/tracing/trace_collection/automatic_instrumentation/dd_libraries/java/?tab=wget
  - https://delta-dev-software.fr/integrating-spring-boot-with-datadog-a-step-by-step-tutorial
  - https://docs.spring.io/spring-boot/docs/3.2.8/reference/htmlsingle/ を datadog で検索

- [ ] kotlin-result の導入

- [ ] OpenTelemetry について調査

- [ ] @Transactional の挙動について実験して理解する

- [ ] ByteBuddy について試してみる
    - https://www.baeldung.com/byte-buddy

- [x] AOP
    - [x] [Logging with AOP](https://www.baeldung.com/spring-aspect-oriented-programming-logging)
    - [x] https://www.baeldung.com/spring-aop-test-aspect]
    - [x] [独自アノテーションを対象に AOP](https://tech.excite.co.jp/entry/2023/12/06/100456)
    - [x] AOP と MDC の組み合わせ
    - [ ] ドキュメント通読
        - https://docs.spring.io/spring-framework/reference/core/aop.html
        - https://spring.pleiades.io/spring-framework/reference/core/aop.html
