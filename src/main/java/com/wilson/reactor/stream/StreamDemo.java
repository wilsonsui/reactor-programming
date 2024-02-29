package com.wilson.reactor.stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.sound.midi.Soundbank;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author wilson
 */
public class StreamDemo {

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    static class Person {
        private String name;
        private String gender;
        private Integer id;
    }

    public static void main(String[] args) {
        List<Person> personList = List.of(
                new Person("王 五", "男", 1),
                new Person("张 三", "女", 2),
                new Person("刘 六", "男", 3),
                new Person("刘 八", "人", 4)
        );
        //分组操作
        Map<String, List<Person>> collect = personList.stream().collect(Collectors.groupingBy(
                e -> e.getGender()
        ));

        Map<Integer, Person> collect1 = personList.stream().collect(Collectors.toMap(
                e -> e.getId(),
                e -> e
        ));

        System.out.println(collect);


        //1、挑出年龄
//        personList.stream()
//                .takeWhile(e -> e.getAge() < 1)//filter会遍历流的每一个元素，但是takeWhile是一个短路操作，遇到不满足条件的元素就结束流操作
//                .filter(person -> {
////                    System.out.println("filter==" + person);
//                    return person.getAge() > 11;
//                }) //过滤年龄
//                .peek(e -> {//peek是一个中间操作，返回的流与原来的流是同一个流
////                    System.out.println("peek===" + e);
//                })
//                .limit(1)
//                .map(e -> {
//                    System.out.println("map===" + e);
//                    return e.getName();
//                }) //获取所有人的姓名
//                .flatMap(e -> Arrays.stream(e.split(" ")))//一对多
//                .distinct()//去重
//                .sorted(String::compareTo)
//                .forEach(System.out::println);

    }
//    public static void main(String[] args) {
//        Integer integer = Stream.of(1, 2, 4, 5, 6, 7, 8, 8, 13, 666)
//                .parallel().filter(e -> {
//                    System.out.println("过滤："+Thread.currentThread().getName());
//                    return e % 2 == 0;
//                })
//                .max((a, b) -> {
//                    System.out.println("计算"+Thread.currentThread().getName());
//                    return a.compareTo(b);
//                }).get();
//        System.out.println(integer);
//
//
//        List.of().stream();
//        Set.of().stream();
//        Map.of().keySet().stream();
//        Map.of().values().stream();
//    }
}
