# デバッグ記録

## 再現

コミット `0c6b1d0` で `mvn --batch-mode test` を実行すると、2回目の `find("b")` で `IllegalStateException: stream has already been operated upon or closed` となる。

## 観測

1回目の検索は `[alice, carol]` を返す。2回目は結果を返す前に例外となる。Streamをフィールドへ保持していることが観測できる。

## 仮説比較

| 仮説 | 実験 | 結果 |
| --- | --- | --- |
| 入力データが壊れている | 1回目の結果を確認する | 正しいため棄却 |
| filter条件が2回目だけ不正 | 新しいStreamを別々に作る | 成功するため棄却 |
| 終端操作でStreamが消費された | 同じインスタンスで2回操作する | IllegalStateExceptionとなり採用 |

## 原因

Streamは遅延評価され、終端操作でパイプラインが実行される。また、同じStreamは一度だけ操作すべきで、再利用できない。[1] バグ状態は検索用データではなく、消費済みのStreamそのものを保持していた。

## 最小修正

`Stream<String>` のフィールドを `Supplier<Stream<String>>` に変更し、`find` の呼び出しごとに `names.get()` で新しいStreamを生成した。修正コミットは `4be8022` である。

## 再発防止テスト

元のテストは2回の検索結果を確認する。修正後は `first=[alice, carol] second=[bob]`、`Tests run: 1, Failures: 0, Errors: 0` となる。

## References

[1] [Java SE 21 API — Stream](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/stream/Stream.html)
