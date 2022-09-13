package no.fintlabs.optional.example;

import no.fintlabs.Student;
import no.fintlabs.StudentRepository;

import java.util.List;
import java.util.Random;

/***
 * Not meant as example. Just for demo purpose
 */
public class SchooleBase {

    private StudentRepository studentRepository;

    public SchooleBase() {
        this.studentRepository = new StudentRepository();
    }

    protected List<Student> getStudents() {
        return studentRepository.getStudents();
    }

    protected boolean getExamResultsHaveBeenSet() {
        return false;
    }

    protected Random getRandomizer() {
        return new Random();
    }

    protected List<Student>  getStudentsOrderedByGradeDesc() {
        // todo
        return getStudents();
    }

    public void addStudent(Student student){

    }
}
