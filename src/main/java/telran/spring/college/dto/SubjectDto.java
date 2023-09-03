package telran.spring.college.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
public class SubjectDto {

	String id;
	String name;
	int hours;
	Long lecturerId;
	SubjectType subjectType;
}
