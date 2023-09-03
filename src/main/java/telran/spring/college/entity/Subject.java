package telran.spring.college.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.spring.college.dto.SubjectDto;
import telran.spring.college.dto.SubjectType;

@Entity
@Table(name = "subjects")
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Subject {

	@Id
	public String id;
	
	String name;
	int hours;
	
	@Enumerated(EnumType.STRING)
	SubjectType type;
	
	@ManyToOne(fetch = FetchType.LAZY) // обеспечивает связь с таблицами
	@JoinColumn(name = "lecturer_id", nullable = true)
	@OnDelete(action = OnDeleteAction.SET_NULL)
	Lecturer lecturer;

	public SubjectDto build() {
		return new SubjectDto(id, name, hours, lecturer == null ? null : lecturer.getId(), type);
	}

	static public Subject of(SubjectDto subject) {
		return new Subject(subject.getId(), subject.getName(), subject.getHours(), subject.getSubjectType(), null);
	}

}
