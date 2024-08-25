# profile の挙動の確認

## profile を指定せずに起動

production を指定していないため `application-default.yml` と `application-production.yml` が使用される。

```shell
# 出力なし
echo $SPRING_PROFILES_ACTIVE

./gradlew :bootRun
```

> 2024-07-29T16:26:57.464+09:00  INFO 56020 --- [           main] k.c.e.d.DemoProfileApplicationKt         : No active profile set, falling back to 1 default profile: "default"

```shell
# application-default.yml から読まれた値
$ curl localhost:28080/hello
Hello from application-default.yml

$ curl localhost:28080/goodbye
Goodbye from application-default.yml

# application.yml から読まれた値
$ curl localhost:28080/port
28080
```
## profile を指定して起動

production を指定したため `application.yml` と `application-production.yml` が使用される。

```shell
export SPRING_PROFILES_ACTIVE=production

./gradlew :bootRun
```

> 2024-07-29T16:24:00.443+09:00  INFO 55275 --- [           main] k.c.e.d.DemoProfileApplicationKt         : The following 1 profile is active: "production"

```shell
# application-production.yml から読まれた値
$ curl localhost:28080/hello
Hello from application-production.yml

$ curl localhost:28080/goodbye
Goodbye from application-production.yml

# application.yml から読まれた値
$ curl localhost:28080/port
28080
```
