package com.wilson.reactor.learn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wilson
 */

interface MyInterface {
    int sum(int i, int j);
}

class MyIterfaceImpl implements MyInterface {
    @Override
    public int sum(int i, int j) {
        return i + j;
    }
}

public class Lambda {
    public static void main(String[] args) {
        MyInterface iterface = new MyIterfaceImpl();
        System.out.println(iterface.sum(1, 2));

        MyInterface iterface1 = (i, j) -> {
            return i * i + j * j;
        };
        System.out.println(iterface1.sum(1, 2));

        List<String> stringList = new ArrayList<>();
        stringList.add("a");
        stringList.add("b");
        stringList.add("c");
        Collections.sort(stringList, (s1, s2) -> -s1.compareTo(s2));
        Collections.sort(stringList, String::compareTo);

    }
}
