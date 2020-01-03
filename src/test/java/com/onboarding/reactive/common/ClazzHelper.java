package com.onboarding.reactive.common;

import akka.NotUsed;
import akka.stream.javadsl.Source;
import reactor.core.publisher.Mono;

import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class ClazzHelper {

    public static Source<Clazz, NotUsed> getClazzSourceById(int id) {
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

    public static Mono<Clazz> getClazzMonoById(int id) {
        return Mono.just(
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

    public static Mono<String> getHeadTeacherNameById(int id) {
        return id == 2 ? Mono.just("teacher")
            : Mono.error(RuntimeException::new);
    }

    public static Mono<String> getAddress(int id) {
        return id == 1 ? Mono.just("address")
            : Mono.error(RuntimeException::new);
    }
}
