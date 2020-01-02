package com.onboarding.reactive.akkastream;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Data
@Builder
@AllArgsConstructor
public class Clazz {

    private int id;

    private boolean isArtClass;

    private List<Student> students;

    public Clazz(boolean isArtClass) {
        this.isArtClass = isArtClass;
        this.students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public static Clazz create(int studentCount, boolean isArtClass) {
        return Clazz.builder()
            .isArtClass(isArtClass)
            .students(
                IntStream.range(0, studentCount)
                    .mapToObj(i -> new Student())
                    .collect(toList())
            )
            .build();
    }

}
