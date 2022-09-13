package no.fintlabs;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentRepository {

    public List<Student> getStudents() {
        return List.of(
                new Student("Jonny Fredrik", Gender.MALE, 22),
                new Student("Guri Mette", Gender.FEMALE, 28),
                new Student("Ole Gunnar", Gender.MALE, 21),
                new Student("Rodney", Gender.MALE, 25),
                new Student("Mia", Gender.FEMALE, 21),
                new Student("Oddvar", Gender.MALE, 58),
                new Student("Kira", Gender.FEMALE, 24)
        );
    }
}
