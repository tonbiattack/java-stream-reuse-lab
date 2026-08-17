package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class StreamSearchTest {

    @Test
    void 同じ検索オブジェクトで複数回検索できる() {
        StreamSearch search = new StreamSearch(List.of("alice", "bob", "carol"));

        List<String> aNames = search.find("a");
        List<String> bNames = search.find("b");

        System.out.println("[evidence] first=" + aNames + " second=" + bNames);

        assertEquals(List.of("alice", "carol"), aNames);
        assertEquals(List.of("bob"), bNames);
    }
}
