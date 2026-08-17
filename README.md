# Java Streamを同じ検索で再利用して失敗する

本ラボは、Streamをフィールドへ保存して複数回の検索に使うと、2回目の終端操作で `IllegalStateException` になる問題を再現します。

## 実行

```bash
mvn --batch-mode test
```

バグ状態は `0c6b1d0` で2回目の検索が `stream has already been operated upon or closed` となります。修正状態は `4be8022` で、`Supplier<Stream<String>>` から検索ごとに新しいパイプラインを作り、同じテストが成功します。

## 学習の流れ

| 段階 | 観測 |
| --- | --- |
| 再現 | 1回目は成功、2回目でIllegalStateException |
| 仮説 | Streamが遅延評価なので保存しても再利用できる |
| 切り分け | 終端操作後のStreamを再操作する最小実験 |
| 修正 | Streamではなく生成関数を保持する |

詳細は `docs/debugging-record.md` を参照してください。

## References

[1] [Java SE 21 API — Stream](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/stream/Stream.html)
