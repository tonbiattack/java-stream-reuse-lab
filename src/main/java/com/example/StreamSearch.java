package com.example;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class StreamSearch {

    private final Supplier<Stream<String>> names;

    public StreamSearch(List<String> names) {
        this.names = () -> names.stream();
    }

    public List<String> find(String query) {
        return names.get().filter(name -> name.contains(query)).toList();
    }
}
