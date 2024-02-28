package com.wilson.reactor.learn;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author wilson
 */
public class FunctionDemo {
    public static void main(String[] args) {
        Supplier<String> supplier = () -> {
            return "12a";
        };
        Predicate<String> isNum = (a) -> {
            return a.matches("\\d+");
        };
        Function<String, Integer> function = Integer::parseInt;
        Consumer<Integer> consumer = (a) -> {
            if (a % 2 == 0) {
                System.out.println("偶数" + a);
            } else {
                System.out.println("奇数" + a);
            }
        };
        String data = supplier.get();
        if (isNum.test(data)) {
            consumer.accept(function.apply(data));
        } else {
            System.out.println("不是数字");
        }
    }
}
