package no.fintlabs;

import lombok.extern.slf4j.Slf4j;
import no.fintlabs.optional.example.School;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;

        List<Student> students = studentRepository.getStudents();

        // Example 1:
        List<Student> femaleOnCampus = students
                .stream()
                .filter(student -> student.getGender().equals(Gender.FEMALE))
                .filter(Student::isLivingOnCampus)
                .collect(Collectors.toList());

        femaleOnCampus.forEach(student -> log.info(student.toString()));


        // Example 2:
        List<Student> studentsSortByAgeDesc = students
                .stream()
                .sorted(Comparator.comparing(Student::getAge).reversed())
                .collect(Collectors.toList());


        // Streams can be saved as variables, but normally we dont.
        Stream<Student> studentsStream = students.stream();
        Stream<Student> femaleStream = studentsStream.filter(student -> student.getGender().equals(Gender.FEMALE));
        List<Student> result1 = femaleStream.collect(Collectors.toList());
        // This will end in exception:
        // long count = femaleStream.count();


        //1. DU OPPRETTER EN STREAM
        Stream.of(1, 2, 3);
        Stream.generate(Math::random);
        IntStream.range(0, 100);
        IntStream.iterate(0, i -> i + 2);


        //2. DU SPESIFISERER MELLOMLIGGENDE OPERASJONER SOM TRANSFORMERER DEN OPPRINNELIGE STREAM'EN TIL EN ANNEN, I ET ELLER FLERE STEG

        // Vi har sett flere eksempler på filter

        // Map endrer fra Stream av en ting til Stream av en annen ting (her fra Student til String)
        List<String> names = students
                .stream()
                .map(s -> s.getName())
                .collect(Collectors.toList());

        // Primitive typer kan mappes til boxed type (Integer), men her endres Streamen til en IntStream
        students
                .stream()
                .mapToInt(student -> student.getAge())
                .skip(100)
                .limit(50)
                .max();

        // Skip, limit og distinct endrer også på innholdet i streamen
        Stream.generate(Math::random).limit(1000).distinct();


        //3. DU TERMINERER STRØMMEN TIL ET RESULTAT
        // Vi har sett flere eksempler som terminerer til en liste

        // Lagrer resultatet til en kommaseparert liste:
        String names2 = students
                .stream()
                .map(student -> student.getName())
                .collect(Collectors.joining(", "));

        // Stream ender opp i en forEach:
        students.stream()
                .forEach(student -> log.info(student.toString()));

        // FindFirst når vi forventer ett element.
        // Returnerer Optinal, men her kaster vi feil hvis den er empty (Se School)
        Student oleGunnar = students
                .stream()
                .filter(student -> student.getName().equals("Ole Gunnar"))
                .findFirst()
                .orElseThrow();

        boolean areAllStudentsOlderEnough = students.stream().allMatch(student -> student.getAge() > 20);

        boolean anyOldStudents = students.stream().anyMatch(student -> student.getAge() > 60);

        boolean allFemale = students.stream().noneMatch(student -> student.getGender().equals(Gender.MALE));

        students.stream().max(Comparator.comparing(Student::getAge)).ifPresent(student -> log.info(student.toString()));

        IntSummaryStatistics statistics = students.stream().collect(Collectors.summarizingInt(Student::getAge));
        log.info("Max: " + statistics.getMax());
        log.info("Sum: " + statistics.getSum());
        log.info("Average: " + statistics.getAverage());
        log.info("Min: " + statistics.getMin());
        log.info("Count: " + statistics.getCount());


        // To eksempler jeg ikke rakk:

        // Reduksjon (denne summerer, men kan brukes til å aggregere mer komplekse resultat):
        Optional<Integer> sum = students
                .stream()
                .map(student -> student.getAge())
                .reduce((x, y) -> x + y);

        // Grouping
        Map<Gender, List<Student>> personsByGender = students.stream()
                .collect(Collectors.groupingBy(Student::getGender));
    }

    private void lambda() {
        List<Student> students = studentRepository.getStudents();

        // Old school - with foreach
        for (Student student : students) {
            log.info(student.toString());
        }

        // Same code with lambda
        students.forEach(student -> log.info(student.toString()));
    }

    private void methodReferece() {
        List<Student> students = studentRepository.getStudents();

        School school = new School();
        // Adding students with lambda
        students.forEach(student -> school.addStudent(student));

        // Same code with method reference
        students.forEach(school::addStudent);
    }
}
