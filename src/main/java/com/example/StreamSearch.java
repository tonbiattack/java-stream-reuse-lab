package com.example;

import java.util.List;
import java.util.stream.Stream;

public class StreamSearch {

    private final Stream<String> names;

    public StreamSearch(List<String> names) {
        this.names = names.stream();
    }

    public List<String> find(String query) {
        return names.filter(name -> name.contains(query)).toList();
    }
}
