package telran.spring.college.batch;

import java.time.LocalDate;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.spring.college.dto.*;
import telran.spring.college.service.CollegeService;

@Component
@RequiredArgsConstructor
@Slf4j
public class RandomDbCreation {

	final CollegeService collegeService;

	@Value("${spring.jpa.hibernate.ddl-auto:create}")
	String creationDb;

	@Value("${app.random.students.amount:100}")
	int nStudents;

	@Value("${app.random.subjects.amount:5}")
	int nSubjects;

	@Value("${app.random.lecturers.amount:5}")
	int nLecturers;

	@Value("${app.random.marks.amount:500}")
	int nMarks;

	@Value("${app.random.students.initial-id:100000}")
	int initialStudentId;

	@Value("${app.random.lecturers.initial-id:500000}")
	int initialLecturerId;

	@Value("${app.random.cities.amount:3}")
	private int nCities;

	int countErrors = 0;

	@PostConstruct
	void createDB() {
		if (creationDb.equals("create")) {
			createStudents();
			createLecturers();
			createSubjects();
			createMarks();
			if (countErrors > 0) {
				log.warn("DB created with {} exceptions", countErrors);
			} else {
				log.debug("DB created without exceptions");
			}
		} else {
			log.debug("DB has not been created");
		}
	}

	private void createStudents() {
		IntStream.rangeClosed(initialStudentId, initialStudentId + nStudents).mapToObj(index -> getRandomPerson(index))
				.forEach(student -> {
					try {
						collegeService.addStudent(student);
					} catch (Exception e) {
						log.error("error: {}", e);
						countErrors++;
					}
				});
	}

	private void createLecturers() {
		IntStream.rangeClosed(initialLecturerId, initialLecturerId + nLecturers)
				.mapToObj(index -> getRandomPerson(index)).forEach(lecturer -> {
					try {
						collegeService.addLecturer(lecturer);
					} catch (Exception e) {
						log.error("error: {}", e);
						countErrors++;
					}
				});
	}

// ------------------------------------------------SUBJECTS-START-----------------------------------------------
	private void createSubjects() {
		IntStream.rangeClosed(1, nSubjects).mapToObj(i -> getRandomSubject(i)).forEach(subject -> {
			try {
				collegeService.addSubject(subject);
			} catch (Exception e) {
				log.error("error: {}", e);
				countErrors++;
			}
		});
	}

	private SubjectDto getRandomSubject(int index) {
		String name = "subject" + index;
		int hours = getRandomNumber(50, 500);
		Long lecturer = randomLecturerId();
		SubjectType subjectType = randomSubjectType();
		return new SubjectDto("S" + index, name, hours, lecturer, subjectType);
	}

	private long randomLecturerId() {
		return getRandomNumber(initialLecturerId, initialLecturerId + nLecturers + 1);
	}

	private SubjectType randomSubjectType() {
		SubjectType[] values = SubjectType.values();
		int index = getRandomNumber(0, values.length);
		return values[index];
	}
// ------------------------------------------------SUBJECTS-END-----------------------------------------------

// ------------------------------------------------MARKS-START-----------------------------------------------
	private void createMarks() {
		Stream.generate(() -> getRandomMark()).limit(nMarks).forEach(mark -> {
			try {
				collegeService.addMark(mark);
			} catch (Exception e) {
				log.error("error: {}", e);
				countErrors++;
			}
		});
	}

	private MarkDto getRandomMark() {
		long studentId = randomStudentId();
		String subjectId = randomSubjectId();
		int mark = randomScore();
		return new MarkDto(null, studentId, subjectId, mark);
	}

	private long randomStudentId() {
		return getRandomNumber(initialStudentId, initialStudentId + nStudents + 1);
	}

	private String randomSubjectId() {
		return "S" + getRandomNumber(1, nSubjects + 1);
	}

	private int randomScore() {
		return getRandomNumber(60, 101);
	}
// ------------------------------------------------MARKS-END-----------------------------------------------

// ------------------------------------------------GENERAL-START-----------------------------------------------
	private PersonDto getRandomPerson(long index) {
		String name = "name" + index;
		String birthDate = randomBirthDate();
		String city = "city" + getRandomNumber(1, nCities);
		String phone = randomPhone();
		return new PersonDto(index, name, birthDate, city, phone);
	}

	private String randomBirthDate() {
		int year = getRandomNumber(1990, 2000);
		int month = getRandomNumber(1, 13);
		int day = getRandomNumber(1, 28);
		return LocalDate.of(year, month, day).toString();
	}

	private String randomPhone() {
		String code = "05" + getRandomNumber(0, 10);
		int number = getRandomNumber(1000000, 10000000);
		return code + "-" + number;
	}

	private int getRandomNumber(int min, int max) {
		return new Random().nextInt(min, max);
	}

}
