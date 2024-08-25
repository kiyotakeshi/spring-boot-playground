# log

- logger の動きについて検証
  - log level の指定
  - logback の設定ファイル(XML)の動作確認

- Actuator の動作確認 + JSON 形式でのログ出力

- MDC の動きについて検証

- Servlet filter にて MDC の情報を差し込むように検証

- logger の設定方法を通じて reflection について検証

## tips

### SLF4j = Simple Logging Facade for Java (SLF4J) とは

https://www.slf4j.org/

> The Simple Logging Facade for Java (SLF4J) serves as a simple facade or abstraction for various logging frameworks (e.g. java.util.logging, logback, log4j) allowing the end user to plug in the desired logging framework at deployment time.

名は体を表すだった。

SLF4j は facade で logback, log4j などすきな logging framework を使えば良い。

※入り口を示す facade pattern の facade: https://en.wikipedia.org/wiki/Facade_pattern <br>
※実際に [logback, log4j を切り替えている例](https://qiita.com/NagaokaKenichi/items/9febd2e559331152fcf8)

[SLF4JとLogbackは2021年現在では積極採用しない方が良い（2023年12月 追記）](https://blog.kengo-toda.jp/entry/2021/05/31/200807) という記事を昔見たが、
[SLF4JとLogbackは2023年末現在で積極採用していいよ](https://blog.kengo-toda.jp/entry/2023/12/15/111624) になったらしい。

---
- [Java のログ周りの歴史的経緯が学べてよいスライド](https://www.slideshare.net/miyakawataku/concepts-and-tools-of-logging-in-java)