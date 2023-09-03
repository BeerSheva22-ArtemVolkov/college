package telran.spring.college.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import telran.spring.college.dto.PersonDto;

@Entity
//@Table(name = "students")
public class Student extends Person {

	// orphanRemoval = true означает, что студент удалится, как только у него удалится последняя оценка
//	@OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@Getter
	// Eager - всегда делай join 
	// Lazy (по ум) - 
	@OneToMany(mappedBy = "student") // 1 студент, много оценок
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
