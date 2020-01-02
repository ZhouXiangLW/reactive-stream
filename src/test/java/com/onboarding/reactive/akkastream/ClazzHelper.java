package com.onboarding.reactive.akkastream;

import akka.NotUsed;
import akka.stream.javadsl.Source;

import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class ClazzHelper {

    public static Source<Clazz, NotUsed> getClazzById(int id) {
        return Source.single(
            Clazz.builder()
                .id(id)
                .students(
                    IntStream.range(0, id + 1)
                        .mapToObj(i -> new Student())
                        .collect(toList())
                )
                .build()
        );
    }

}
