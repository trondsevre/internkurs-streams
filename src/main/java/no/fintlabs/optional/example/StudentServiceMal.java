package no.fintlabs.optional.example;

import lombok.extern.slf4j.Slf4j;
import no.fintlabs.Gender;
import no.fintlabs.Student;
import no.fintlabs.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StudentServiceMal {

    private final StudentRepository studentRepository;

    public StudentServiceMal(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
        List<Student> students = studentRepository.getStudents();

        // LAMBDA
        for (Student student : students) {
            log.info(student.toString());
        }

        students.forEach(student -> log.info(student.toString()));

        // METHOD REFERENCE
        School school = new School();
        students.forEach(student -> school.addStudent(student));
        students.forEach(school::addStudent);

        // STREAMS - først noen eksempler

        // filter
        List<Student> females = students
                .stream()
                .filter(student -> student.getGender().equals(Gender.FEMALE))
                .collect(Collectors.toList());

        //females.forEach(person -> log.info(person.toString()));
        // Vis debug-mulighet i intelliJ

        // filter som method referense
        // .filter(student -> student.isLivingInCampus())
        // .filter(Student::isLivingInCampus)

        // sort
        List<Student> sorted = students
                .stream()
                .sorted(Comparator.comparing(Student::getAge).reversed())
                .collect(Collectors.toList());

        //1. Du oppretter en stream
        //2. Du spesifiserer mellomliggende operasjoner som transformerer den opprinnelige stream'en til en annen, i et eller flere steg
        //3. Du terminerer strømmen til et resultat

        // 1. Du oppretter
        // Stream.of(1, 2, 3)
        // Stream.generate(Math::random);
        // IntStream.range(1, 10);
        // IntStream.iterate(0, i -> i + 2)

        // 2. Du transformerer

        // .filter
        // .map
        // .flatMap

        // limit: .limit(500)
        // Stream.generate(Math::random).limit(100);
        // Skip: skip(1)
        // distinct: .distinct()

        // 3. Du terminerer

        //.collect(Collectors.toList())
        //bare strings .collect(Collectors.joining(", "));
        //.foreach
        //.count()

        // OPTIONAL
        // Se på eksemplet i School
        Optional<Student> oleGunnar = students
                .stream()
                .filter(student -> student.getName().equals("Ole Gunnar"))
                .findFirst();
        // oleGunnar oleGunnar.isEmpty() | oleGunnar.isPresent() & oleGunnar.get()

        // .ifPresent()
        // .orElse(..)
        // .orElseThrow()
        // .orElseGet()

        Optional<Integer> sum = students
                .stream()
                .map(student -> student.getAge())
                .reduce((x, y) -> x + y);

        // All match
        boolean anyMatch = students.stream().allMatch(student -> student.getAge() > 20);
        // Any match
        boolean oldEnough = students.stream().anyMatch(student -> student.getAge() > 20);

        // No match
        boolean anyBoys = students.stream().noneMatch(student -> student.getGender().equals(Gender.MALE));
        boolean anyTronds = students.stream().noneMatch(student -> student.getName().contains("Trond"));

        // max / min
        students.stream().max(Comparator.comparing(Student::getAge)).ifPresent(student -> log.info(student.toString()));
        students.stream().mapToInt(student -> student.getAge()).max().ifPresent(max -> log.info("Max: " + max));

        // statistics
        IntSummaryStatistics namesStatistics = students
                .stream()
                .map(Student::getName)
                .collect(Collectors.summarizingInt(String::length));


        // Grouping
        Map<Gender, List<Student>> personsByGender = students.stream()
                .collect(Collectors.groupingBy(Student::getGender));


// 1. A stream does not store its elements. They may be stored in an underlying collection or generated on demand.
// 2. Stream operations don’t mutate their source. Instead, they return new streams that hold the result.
// 3. Stream operations are lazy when possible. This means they are not executed until their result is needed.

// Lambda -> Se nettside oreilly
    }
}
