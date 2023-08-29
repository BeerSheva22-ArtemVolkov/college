package telran.spring.college.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import telran.spring.college.dto.PersonDto;

@Entity
//@Table(name = "lectureres")
@NoArgsConstructor
public class Lecturer extends Person {

	private Lecturer(PersonDto person) {
		super(person);
	}

	public static Lecturer of(PersonDto person) {
		// possible additional validation
		return new Lecturer(person);
	}
}
