package telran.spring.college.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import telran.spring.college.dto.MarkDto;

@Entity
@NoArgsConstructor
@Table(name = "marks")
public class Mark {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Автоматически генерируется и проверяется на уникальность
	int id;
	
	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false) // join связывает сущность Student с сущностью Mark
	Student student;
	
	@ManyToOne
	@JoinColumn (name = "subject_id", nullable = false)
	Subject subject;
	
	@Column(nullable = false)
	int mark;

	public Mark(Student student, Subject subject, int mark) {
		this.student = student;
		this.subject = subject;
		this.mark = mark;
	}
	
	public MarkDto build() {
		return new MarkDto(id, student.id, subject.id, mark);
	}
	
}
