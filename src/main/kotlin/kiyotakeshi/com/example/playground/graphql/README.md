# Spring for GraphQL

- [x] N+1 を起こして DataLoader(`@BatchMapping`) にて解消 

- [x] DataLoader そのものについて調べる

- [x] DataLoader に `@Argument` を受け取る挙動を検証

---
## ドキュメントに目を通しておく

https://www.graphql-java.com/documentation/batching/

> Using java-dataloader will help you to make this a more efficient process by both caching and batching requests for that graph of data items.

java-dataloader はデータ項目のグラフに対するリクエストをバッチリクエストしキャッシュする

> If dataloader has seen a data item before, it will have cached the value and will return it without having to ask for it again.

dataloader が以前にデータ項目を見た場合、値をキャッシュして再度要求することなく返す

> A naive implementation would call a DataFetcher to retrieve a person object every time it was invoked.

素朴な実装だと呼び出されるたびに DataFetcher を invoke する

> As graphql descends each level of the query (...), the data loader is called to "promise" to deliver a person object.

graphql が query の各レベルを降下するたびに、data loader が呼ばれて promise を提供する

> At each level dataloader.dispatch() will be called to fire off the batch requests for that part of the query. With caching turned on (the default) then any previously returned person will be returned as-is for no cost.

各レベルで dataloader.dispatch() が呼び出され、クエリのその部分のバッチ要求が実行される
キャッシュが有効になっていたら以前に返されたものはコストなしで返される

> If you are serving web requests then the data can be specific to the user requesting it.
> If you have user specific data then you will not want to cache data meant for user A to then later give it to user B in a subsequent request.

リクエストしたユーザ固有のデータを提供している場合、ユーザ A 用のデータをキャッシュしてから後でユーザ B にそれを次のリクエストで提供することは避けたい

> The scope of your DataLoader instances is important. You will want to create them per web request to ensure data is only cached within that web request and no more

DataLoader のインスタンススコープはそのウェブリクエストないでのみキャッシュされるようにインスタンス作成する

---
https://docs.spring.io/spring-graphql/reference/request-execution.html#execution.batching

> GraphQL Java provides a DataLoader mechanism for batch loading of related entities.

DataLoader は GraphQL Java の概念で、関連するエンティティを一括ロードする仕組み。

> 1. Register DataLoader's in the DataLoaderRegistry that can load entities, given unique keys.

ユニークなキーを指定して entity をロードできる DataLoader を DataLoaderRegistry に登録する。

> 2. DataFetcher's can access DataLoader's and use them to load entities by id.

DataFetcher が DataLoader にアクセスし、id で entity をロードする。

> 3. A DataLoader defers loading by returning a future so it can be done in a batch.

DataLoader は future を返してロードを遅延させ、バッチで行うことができる。

> 4. DataLoader's maintain a per request cache of loaded entities that can further improve efficiency.

DataLoader はリクエストごとのキャッシュを維持し、効率をさらに向上させる。

---

https://docs.spring.io/spring-graphql/reference/controllers.html#controllers.batch-mapping

> Batch Loading addresses the N+1 select problem through the use of an org.dataloader.
> DataLoader to defer the loading of individual entity instances, so they can be loaded together.

個々のエンティティのロードを遅延しまとめてロードすることで N+1 に対応する

```java
	@BatchMapping
	public Mono<Map<Book, Author>> author(List<Book> books) {
		// ...
	}
```

> The above becomes a batch loading function in the BatchLoaderRegistry where keys are Book instances and the loaded values their authors.

BatchLoaderRegistry のロード関数となって key は Book instance で、値は Author になる

> In addition, a DataFetcher is also transparently bound to the author field of the type Book,
> which simply delegates to the DataLoader for authors, given its source/parent Book instance.

DataFetcher は透過的に Book 型の author フィールドにバインドされ、親の Book インスタンスが与えられると author の DataLoader に委譲する。

> To be used as a unique key, Book must implement hashcode and equals.

(Map の key にするから) Book の hashcode と equals は実装が必要。

@BatchMapping を付与するメソッドの引数と戻り値の型は指定あり
https://docs.spring.io/spring-graphql/reference/controllers.html#controllers.batch-mapping.signature
https://docs.spring.io/spring-graphql/reference/controllers.html#controllers.batch-mapping.return.values













