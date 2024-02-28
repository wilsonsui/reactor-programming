package com.wilson.reactor.stream;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author wilson
 */
public class StreamDemo {
    public static void main(String[] args) {
        Integer integer = Stream.of(1, 2, 4, 5, 6, 7, 8, 8, 13, 666)
                .parallel().filter(e -> {
                    System.out.println("过滤："+Thread.currentThread().getName());
                    return e % 2 == 0;
                })
                .max((a, b) -> {
                    System.out.println("计算"+Thread.currentThread().getName());
                    return a.compareTo(b);
                }).get();
        System.out.println(integer);


        List.of().stream();
        Set.of().stream();
        Map.of().keySet().stream();
        Map.of().values().stream();
    }
}
