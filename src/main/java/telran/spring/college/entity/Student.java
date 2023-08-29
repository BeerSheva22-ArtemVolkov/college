package telran.spring.college.entity;

import jakarta.persistence.*;
import telran.spring.college.dto.PersonDto;

@Entity
//@Table(name = "students")
public class Student extends Person {

	public Student() {

	}

	private Student(PersonDto person) {
		super(person);
	}

	public static Student of(PersonDto person) {
		return new Student(person);
	}
}
