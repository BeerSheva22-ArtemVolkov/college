package telran.spring.college.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import telran.spring.college.dto.SubjectDto;
import telran.spring.college.dto.SubjectType;

@Entity
@Table(name = "subjects")
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Subject {

	@Id
	String id;
	String name;
	int hours;
	@Enumerated(EnumType.STRING)
	
	SubjectType type;
	@ManyToOne // обеспечивает связь с таблицами
	@JoinColumn(name = "lecturer_id", nullable = true)
	Lecturer lecturer;

	public SubjectDto build() {
		return new SubjectDto(id, name, hours, lecturer == null ? null : lecturer.id, type);
	}

	static public Subject of(SubjectDto subject) {
		return new Subject(subject.getId(), subject.getName(), subject.getHours(), subject.getSubjectType(), null);
	}

}
