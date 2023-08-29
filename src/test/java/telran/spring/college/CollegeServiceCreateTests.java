package telran.spring.college;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import telran.spring.college.dto.*;
import telran.spring.college.service.CollegeService;
import telran.spring.exceptions.NotFoundException;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
class CollegeServiceCreateTests {

	private static final long ID_STUDENT = 124;
	private static final long ID_LECTURER = 123;

	@Autowired
	CollegeService service;

	PersonDto lecturerDto = new PersonDto(ID_LECTURER, "Vasya", LocalDate.now().toString(), null, null);
	PersonDto lecturerDto1 = new PersonDto(null, "Sara", "2000-01-01", null, null);
	PersonDto studentDto = new PersonDto(ID_STUDENT, "Petya", LocalDate.now().toString(), null, null);
	PersonDto studentDto1 = new PersonDto(null, "Yosef", "2000-01-01", null, null);
	SubjectDto subjectDto = new SubjectDto("S1", "Java", 100, null, SubjectType.BACK_END);
	SubjectDto subjectDto1 = new SubjectDto("S2", "Java", 100, ID_LECTURER, SubjectType.BACK_END);
	SubjectDto subjectDto2 = new SubjectDto("S3", "Java", 100, ID_LECTURER + 10, SubjectType.BACK_END);
	SubjectDto subjectDto3 = new SubjectDto("S1", "Java", 100, null, SubjectType.BACK_END);

	@Test
	void contextLoads() {
	}

	@Test
	@Order(1)
	void addLecturerTest() {
		PersonDto lecturerActual = service.addLecturer(lecturerDto);
		assertEquals(ID_LECTURER, lecturerActual.getId());
		PersonDto lecturerActual1 = service.addLecturer(lecturerDto1);
		assertEquals("Sara", lecturerActual1.getName());
		assertThrows(Exception.class, () -> service.addLecturer(lecturerDto));
	}

	@Test
	@Order(2)
	void addStudentTest() {
		PersonDto studentActual = service.addStudent(studentDto);
		assertEquals(ID_STUDENT, studentActual.getId());
		PersonDto studentActual1 = service.addStudent(studentDto1);
		assertEquals("Yosef", studentActual1.getName());
		assertThrows(Exception.class, () -> service.addStudent(studentDto));
	}

	@Test
	@Order(3)
	void addSubject() {
		SubjectDto subjectActual = service.addSubject(subjectDto);
		assertEquals(subjectDto.getId(), subjectActual.getId());
		SubjectDto subjectActual1 = service.addSubject(subjectDto1);
		assertEquals(subjectDto1.getId(), subjectActual1.getId());
		assertThrowsExactly(NotFoundException.class, () -> service.addSubject(subjectDto2));
		assertThrowsExactly(IllegalStateException.class, () -> service.addSubject(subjectDto3));
	}

	@Test
	@Order(4)
	void addMark() {
		MarkDto markDto = new MarkDto(null, ID_STUDENT, subjectDto.getId(), 100);
		MarkDto markDtoNoStudent = new MarkDto(null, ID_STUDENT + 10, subjectDto.getId(), 100);
		MarkDto markDtoNoSubject = new MarkDto(null, ID_STUDENT, "XXXX", 100);

		MarkDto markDtoActualDto = service.addMark(markDto);
		assertEquals(1, markDtoActualDto.getId());
		assertThrowsExactly(NotFoundException.class, () -> service.addMark(markDtoNoStudent));
		assertThrowsExactly(NotFoundException.class, () -> service.addMark(markDtoNoSubject));
	}

}
