package no.fintlabs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Student {

    private String name;
    private Gender gender;
    private int age;

    public boolean isLivingOnCampus(){
        // Just for demo
        return Math.random() < 0.5;
    }
}
