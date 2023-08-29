package telran.spring.college;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import telran.spring.college.dto.PersonDto;
import telran.spring.college.entity.Subject;
import telran.spring.college.repo.StudentRepository;
import telran.spring.college.repo.SubjectRepository;
import telran.spring.college.service.CollegeService;
import telran.spring.exceptions.NotFoundException;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CollegeServiceUpdateDeleteTests {

	static final String SUBJECT_ID = "S1";
	static final Long LECTURER_ID = 421L;
	static final int HOURS = 200;
	private static Long STUDENT_REMOVED_ID_0 = 126L;
	private static Long STUDENT_REMOVED_ID_1 = 124L;
	private static Long STUDENT_REMOVED_ID_2 = 125L;

	@Autowired
	CollegeService collegeService;
	@Autowired
	SubjectRepository subjectRepository;
	@Autowired
	StudentRepository studentRepository;

	@Test
	@Order(1)
	@Sql(scripts = { "college-read-test-script.sql" })
	void updateHours() {
		collegeService.updateHours(SUBJECT_ID, HOURS);
		assertThrowsExactly(NotFoundException.class, () -> collegeService.updateHours(SUBJECT_ID + 10, HOURS));
	}

	@Test
	@Order(3)
	@Sql(scripts = { "college-read-test-script.sql" })
	void updateLecturer() {
		collegeService.updateLecturer(SUBJECT_ID, LECTURER_ID);
		assertThrowsExactly(NotFoundException.class, () -> collegeService.updateLecturer(SUBJECT_ID + 10, LECTURER_ID));
		assertThrowsExactly(NotFoundException.class, () -> collegeService.updateLecturer(SUBJECT_ID, LECTURER_ID + 10));
	}

	@Test
	@Order(2)
	@Transactional(readOnly = true) // тк обращаемся к репозиторию
	void updateHoursTest() {
		Subject subject = subjectRepository.findById(SUBJECT_ID).get();
		assertEquals(HOURS, subject.getHours());
	}

	@Test
	@Order(4)
	@Transactional(readOnly = true)
	void updateLecturerTest() {
		Subject subject = subjectRepository.findById(SUBJECT_ID).get();
		assertEquals(LECTURER_ID, subject.getLecturer().getId());
	}
	
	@Test
	@Order(5)
	void removeStudentsNoMark() {
		List<PersonDto> studentsGoingToBeRemoved = collegeService.removeStudentsNoMarks();
		assertEquals(1, studentsGoingToBeRemoved.size());
	}
	
	@Test
	@Order(6)
	@Transactional(readOnly = true)
	void removeStudentsNoMarkTest() {
		assertNull(studentRepository.findById(STUDENT_REMOVED_ID_0).orElse(null));
	}
	
	@Test
	@Order(7)
	@Sql(scripts = { "college-read-test-script.sql" })
	void removeStudentsLessMark() {
		List<PersonDto> studentsGoingToBeRemoved = collegeService.removeStudentsLessMarks(3);
		assertEquals(3, studentsGoingToBeRemoved.size());
	}
	
	@Test
	@Order(8)
	@Transactional(readOnly = true)
	void removeStudentsLessMarkTest() {
		assertNull(studentRepository.findById(STUDENT_REMOVED_ID_0).orElse(null));
		assertNull(studentRepository.findById(STUDENT_REMOVED_ID_1).orElse(null));
		assertNull(studentRepository.findById(STUDENT_REMOVED_ID_2).orElse(null));
	}
	
}
