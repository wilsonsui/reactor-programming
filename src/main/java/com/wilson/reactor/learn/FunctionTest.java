package com.wilson.reactor.learn;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author wilson
 */
public class FunctionTest {

    public static void main(String[] args) {
        BiConsumer biConsumer = (a, b) -> {
            System.out.println("1111");
        };
        biConsumer.accept(1, 2);
        //有入参，有出参
        Function<String, Integer> function = (String string) -> Integer.parseInt(string);
        System.out.println(function.apply("123"));

        Runnable runnable= () -> {
            System.out.println("runnable");
        };
        runnable.run();
        Supplier supplier = () -> {
            return "supplier";
        };
        Object object = supplier.get();

        Predicate<Integer> predicate = (a)->{
            return a%2==0;
        };
        //反向判断
        System.out.println(predicate.negate().test(1));
        //正向判断
        System.out.println(predicate.test(1));

    }
}
