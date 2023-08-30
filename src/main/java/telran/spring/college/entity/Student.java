package telran.spring.college.entity;

import java.util.List;

import jakarta.persistence.*;
import telran.spring.college.dto.PersonDto;

@Entity
//@Table(name = "students")
public class Student extends Person {

	@OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE) // 1 студент, много оценок
	List<Mark> marks;
	
	public Student() {

	}

	private Student(PersonDto person) {
		super(person);
	}

	public static Student of(PersonDto person) {
		return new Student(person);
	}
	
	
	
}


