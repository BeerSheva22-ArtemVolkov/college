package telran.spring.college;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import telran.spring.college.dto.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import telran.spring.college.service.CollegeService;

@SpringBootTest
class ReadCollegeTest {

	@Autowired
	CollegeService service;
	
	@Disabled
	@Test
	@Sql(scripts = {"college-read-test-script.sql"})
	void bestStudentsLecturerTest() {
		List<IdName> actual = service.bestStudentsLecturer(321, 2);
		assertEquals(2, actual.size());
		assertEquals(actual.get(0).getId(), 127);
		assertEquals(actual.get(0).getName(), "Rivka");
		assertEquals(actual.get(1).getId(), 123);
		assertEquals(actual.get(1).getName(), "Vasya");
	}

	@Disabled
	@Test
	@Sql(scripts = {"college-read-test-script.sql"})
	void studentsAvgMarksGreaterCollegeTest_2() {
		List<IdName> actual = service.studentsAvgMarksGreaterCollege(2);
		assertEquals(3, actual.size());
		assertEquals(actual.get(0).getId(), 123);
		assertEquals(actual.get(0).getName(), "Vasya");
		assertEquals(actual.get(1).getId(), 127);
		assertEquals(actual.get(1).getName(), "Rivka");
		assertEquals(actual.get(2).getId(), 125);
		assertEquals(actual.get(2).getName(), "Yosef");
	}
	
	@Disabled
	@Test
	@Sql(scripts = {"college-read-test-script.sql"})
	void studentsAvgMarksGreaterCollegeTest_3() {
		List<IdName> actual = service.studentsAvgMarksGreaterCollege(3);
		assertEquals(2, actual.size());
		assertEquals(actual.get(0).getId(), 123);
		assertEquals(actual.get(0).getName(), "Vasya");
		assertEquals(actual.get(1).getId(), 127);
		assertEquals(actual.get(1).getName(), "Rivka");
	}
	
	@Disabled
	@Test
	@Sql(scripts = {"college-read-test-script.sql"})
	void studentsAvgMarksGreaterCollegeTest_4() {
		List<IdName> actual = service.studentsAvgMarksGreaterCollege(4);
		assertEquals(1, actual.size());
		assertEquals(actual.get(0).getId(), 123);
		assertEquals(actual.get(0).getName(), "Vasya");
	}
	
	@Test
	@Sql(scripts = {"college-read-test-script.sql"})
	void studentsAvgMarks() { 
		List<IdNameMark> actual = service.findStudentsAvgMarks();
		assertEquals(5, actual.size());
		assertEquals(actual.get(0).getMark(), 100);
		assertEquals(actual.get(1).getMark(), 90);
		assertEquals(actual.get(2).getMark(), 80);
		assertEquals(actual.get(3).getMark(), 20);
		assertEquals(actual.get(4).getMark(), 0); 
	}
}
