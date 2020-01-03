package com.onboarding.reactive.projectreactor;

import com.onboarding.reactive.common.Clazz;
import com.onboarding.reactive.common.ListPublisher;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class ReactiveStreamBasic {

    @Test
    void should_put_all_students_in_art_class_in_one_art_class() {
        // Given
        List<Clazz> clazzes = Arrays.asList(
            Clazz.create(10, true),
            Clazz.create(5, false),
            Clazz.create(20, true),
            Clazz.create(15, false),
            Clazz.create(10, true)
        );

        // when
        Mono<Clazz> process = Mono.just(null);

        Flux.fromIterable(clazzes);
        // todo: add operators to put all student in art class

        // then
        StepVerifier.create(process)
            .expectNextMatches(clazz -> clazz.getStudents().size() == 40)
            .verifyComplete();
    }

    @Test
    void should_get_classes_by_clazz_id() {
        // given
        List<Integer> clazzIds = Arrays.asList(1, 2, 3, 4, 5);

        Mono<Integer> result = Flux.fromIterable(clazzIds)
            .then(Mono.just(1));
        // todo： add operators to get student count in given class, get class via ClazzHelper.getClazzMonoById(int)

        StepVerifier.create(result)
            .expectNextMatches(count -> count == 20)
            .verifyComplete();
    }

    @Test
    void should_fill_the_given_clazz() {
        // given
        Clazz clazz = Clazz.builder().addressId(1)
            .headTeacherId(2)
            .build();

        // when
        Mono<Clazz> result =
            Mono.just(clazz);
        // todo: fill head teacher name and address, use ClazzHelper.getHeadTeacherNameById(int)
        //  && ClazzHelper.getAddress(int)

        // then
        StepVerifier.create(result)
            .expectNextMatches(c -> c.getAddress().equals("address")
                && c.getHeaderTeacherName().equals("teacher"))
            .verifyComplete();

    }

    @Test
    void should_return_empty_clazz_if_error_occurred() {
        // given
        Clazz clazz = Clazz.builder().addressId(2)
            .headTeacherId(2)
            .build();

        // when
        Mono<Clazz> result = Mono.just(clazz);
        // todo: add operator to return empty clazz when error occurred

        //Then
        StepVerifier.create(result)
            .expectNext(new Clazz(true))
            .verifyComplete();
    }

    @Test
    void should_return_default_address_if_error_occurred() {
        // given
        Clazz clazz = Clazz.builder().addressId(2)
            .headTeacherId(2)
            .build();

        // when
        Mono<Clazz> result = Mono.just(clazz);
        //todo: add operator to set default address when error occurred

        //Then
        StepVerifier.create(result)
            .expectNextMatches(c -> c.getAddress().equals("default address"))
            .verifyComplete();
    }

    @Test
    void should_skip_if_error_occurred() {
        // Given and When
        Flux<Integer> result = Flux.range(1, 9)
            .map(i -> {
                if (i == 5) {
                    throw new RuntimeException("error");
                }
                return i;
            });

        // Then
        StepVerifier.create(result)
            .expectNextCount(8)
            .verifyComplete();
    }

    @Test
    void name() {

        // Given
        ListPublisher<Integer> listPublisher = new ListPublisher<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

        // when
        Flux<Integer> flux = Flux.just(0);
        // todo: create a flux with list publisher

        // then
        StepVerifier.create(flux)
            .expectNextCount(9)
            .verifyComplete();
    }
}
