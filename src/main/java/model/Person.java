package model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.enums.Gender;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@Builder
@NoArgsConstructor
@MappedSuperclass
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String family;
    private String nationalCode;
    private String phoneNumber;
    private Gender gender;
    private  int age;


    @Override
    public String toString() {
        return "Person{" +
                ", name='" + name + '\'' +
                ", family='" + family + '\'' +
                ", nationalCode='" + nationalCode + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                '}';
    }
}
