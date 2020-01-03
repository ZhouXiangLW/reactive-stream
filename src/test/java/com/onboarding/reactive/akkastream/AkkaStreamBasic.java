package com.onboarding.reactive.akkastream;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Status;
import akka.japi.Pair;
import akka.stream.CompletionStrategy;
import akka.stream.Graph;
import akka.stream.SourceShape;
import akka.stream.javadsl.Keep;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import com.onboarding.reactive.common.Clazz;
import com.onboarding.reactive.common.Student;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AkkaStreamBasic {

    private ActorSystem actorSystem = ActorSystem.create();

    @Test
    void should_put_all_student_in_art_class_in_a_new_art_class() throws ExecutionException, InterruptedException {
        // Given
        List<Clazz> clazzes = Arrays.asList(
            Clazz.create(10, true),
            Clazz.create(5, false),
            Clazz.create(20, true),
            Clazz.create(15, false),
            Clazz.create(10, true)
        );

        // When
        CompletionStage<Clazz> clazzCompletionStage = Source.from(clazzes)
            // todo: add some operators to put all student in art class in a new art class
            .runWith(Sink.last(), actorSystem);

        //then
        Clazz clazz = clazzCompletionStage.toCompletableFuture().get();
        assertEquals(40, clazz.getStudents().size());
    }

    @Test
    void should_put_giving_student_to_class() throws ExecutionException, InterruptedException {
        List<Student> students = IntStream.range(0, 23)
            .mapToObj(i -> new Student())
            .collect(Collectors.toList());

        CompletionStage<List<Clazz>> clazzCompletionStage = null;
//            Source.from(students)
//            // todo: add operators to put every 5 students in a class
//            .runWith(Sink.seq(), actorSystem);

        List<Clazz> clazzes = clazzCompletionStage.toCompletableFuture().get();
        assertEquals(5, clazzes.size());
        assertEquals(3, clazzes.get(clazzes.size() - 1).getStudents().size());
    }

    @Test
    void should_put_giving_student_to_class_via_flow() throws ExecutionException, InterruptedException {
        List<Student> students = IntStream.range(0, 23)
            .mapToObj(i -> new Student())
            .collect(Collectors.toList());

        // todo: create flow to  put every 5 students in a class


        CompletionStage<List<Clazz>> clazzCompletionStage = null;
//            Source.from(students)
//                .via(flow)
//            .runWith(Sink.seq(), actorSystem);

        List<Clazz> clazzes = clazzCompletionStage.toCompletableFuture().get();
        assertEquals(5, clazzes.size());
        assertEquals(3, clazzes.get(clazzes.size() - 1).getStudents().size());
    }

    @Test
    void should_get_student_count_in_given_clazz() throws ExecutionException, InterruptedException {

        // given
        List<Integer> clazzIds = Arrays.asList(1, 2, 3, 4, 5);

        //when
        CompletionStage<Integer> integerCompletionStage = Source.from(clazzIds)
            // todo： add operators to get student count in given class, get class via ClazzHelper.getClazzSourceById(int)
            .runWith(Sink.last(), actorSystem);

        //  then
        Integer studentCount = integerCompletionStage.toCompletableFuture().get();
        assertEquals(20, studentCount);
    }

    @Test
    void should_get_clazzes_by_given_id_via_class_actor() throws ExecutionException, InterruptedException {
        // given
        List<Integer> clazzIds = Arrays.asList(1, 2, 3, 4, 5);

        // when
        ActorRef clazzActor = null;
        // todo: get a actor ref of ClazzActor

        CompletionStage<List<Clazz>> clazzCompletionStage = null;
//        Source.from(clazzIds)
//            // todo: add some operators to get clazz via actor, use Patterns.ask()
//            .runWith(Sink.seq(), actorSystem);

        List<Clazz> clazzes = clazzCompletionStage.toCompletableFuture().get();
        assertEquals(5, clazzes.size());
    }

    @Test
    void should_resume_when_error_occur() throws ExecutionException, InterruptedException {

        //given and then
        CompletionStage<List<Integer>> numbers = Source.range(1, 10)
            .map(i -> {
                if (i == 5) {
                    throw new RuntimeException("error");
                }
                return i;
            })
            // todo: add operators to handle errors
            .runWith(Sink.seq(), actorSystem);
        // then
        assertEquals(9, numbers.toCompletableFuture().get().size());

    }

    @Test
    void should_put_all_student_in_art_class_in_a_new_art_class_via_actor_ref() throws ExecutionException, InterruptedException {
        // Given
        List<Clazz> clazzes = Arrays.asList(
            Clazz.create(10, true),
            Clazz.create(5, false),
            Clazz.create(20, true),
            Clazz.create(15, false),
            Clazz.create(10, true)
        );
        Pair<ActorRef, CompletionStage<Clazz>> streamResult = new Pair<>(null, null);
        // todo: add operators to put_all_student_in_art_class_in_a_new_art_class, use Source.actorRef()
        ActorRef actorRef = streamResult.first();
        CompletionStage<Clazz> result = streamResult.second();

        // when
        clazzes.forEach(clazz -> actorRef.tell(clazz, ActorRef.noSender()));
        actorRef.tell(new Status.Success(CompletionStrategy.draining()), ActorRef.noSender());

        // then
        Clazz clazz = result.toCompletableFuture().get();
        assertEquals(40, clazz.getStudents().size());
    }

    @Test
    void should_collect_elements_published_by_source() throws ExecutionException, InterruptedException {

        // given
        Source<Integer, NotUsed> range = Source.range(1, 9);
        Graph<SourceShape<Integer>, CompletionStage<List<Integer>>> sourceGraph = null;
        // todo: create source graph with Graph DSL

        // when
        Pair<CompletionStage<List<Integer>>, CompletionStage<List<Integer>>> result = Source.fromGraph(sourceGraph)
            .map(i -> {
                if (i == 5) {
                    throw new RuntimeException("error");
                }
                return i;
            })
            .toMat(Sink.seq(), Keep.both())
            .run(actorSystem);

        // then
        List<Integer> source = result.first().toCompletableFuture().get();
        List<Integer> sink = result.second().toCompletableFuture().get();
        assertEquals(9, source.size());
        assertEquals(8, sink.size());
    }
}
