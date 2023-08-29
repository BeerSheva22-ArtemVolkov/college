package telran.spring.college.service;

import java.util.List;

import telran.spring.college.dto.IdName;
import telran.spring.college.dto.IdNameMark;
import telran.spring.college.dto.MarkDto;
import telran.spring.college.dto.PersonDto;
import telran.spring.college.dto.SubjectDto;

public interface CollegeService {

	PersonDto addStudent(PersonDto person);

	PersonDto addLecturer(PersonDto person);

	SubjectDto addSubject(SubjectDto subject);

	MarkDto addMark(MarkDto mark);

	List<IdName> bestStudentsLecturer(long lecturerId, int nStudents);
	
	List<IdName> studentsAvgMarksGreaterCollege(int nMarksThreshold);

	List<IdNameMark> studentsAvgMarks();
	
	SubjectDto updateHours(String subjectId, int hours);
	
	SubjectDto updateLecturer(String subjectId, Long lecturerId);
	
	List<PersonDto> removeStudentsNoMarks();
	
	List<PersonDto> removeStudentsLessMarks(int nMarks);
}
