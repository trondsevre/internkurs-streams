package no.fintlabs.optional.example;

import no.fintlabs.Student;

import java.util.List;
import java.util.Optional;

public class School extends SchooleBase {

    public Student getRandomStudent() {
        List<Student> students = getStudents();
        return students.get(getRandomizer().nextInt(students.size()));
    }

    public Optional<Student> getBestStudent() {
        if (!getExamResultsHaveBeenSet()) return Optional.empty();
        return Optional.of(getStudentsOrderedByGradeDesc().get(0));
    }


    private void Test() {
        Optional<Student> bestStudent = getBestStudent();
        if (bestStudent.isPresent()){
            Student student = bestStudent.get();
            // Here its safe to handle student
        }

        if (bestStudent.isEmpty()) {
            // Handle student is not found
        }

        // Other ways to handle Optional in Streams
        // bestStudent.ifPresent(student -> );
        // bestStudent.orElse(new DefaultStudent())
        // bestStudent.orElseThrow()
        // etc...
    }
}
