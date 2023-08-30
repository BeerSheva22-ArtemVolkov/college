package telran.spring.college.entity;

import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;
import telran.spring.college.dto.PersonDto;

@Entity
//@Table(name = "students")
public class Student extends Person {

	// orphanRemoval = true означает, что студент удалится, как только у него удалится последняя оценка
//	@OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@OneToMany(mappedBy = "student") // 1 студент, много оценок
	@OnDelete(action = OnDeleteAction.CASCADE)
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
