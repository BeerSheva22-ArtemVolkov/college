package telran.spring.college.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.spring.college.dto.*;
import telran.spring.college.entity.*;
import telran.spring.college.repo.*;
import telran.spring.exceptions.NotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CollegeServiceImpl implements CollegeService {

	final LecturerRepository lecturerRepository;
	final StudentRepository studentRepository;
	final SubjectRepository subjectRepository;
	final MarkRepository markRepository;

	@Value("${app.person.id.min:100000}")
	long minID;
	@Value("${app.person.id.max:999999}")
	long maxID;

	@Override
	@Transactional(readOnly = false)
	public PersonDto addStudent(PersonDto person) {
		if (person.getId() == null) {
			person.setId(getUniqueId(studentRepository));
		}
		Student student = Student.of(person);
		if (studentRepository.existsById(student.getId())) {
			throw new IllegalStateException("Student with given id already exists" + student.getId());
		}
		PersonDto res = studentRepository.save(student).build();
		log.debug("student added {}", res);
		return res;
	}

	@Override
	@Transactional(readOnly = false)
	public PersonDto addLecturer(PersonDto person) {
		if (person.getId() == null) {
			person.setId(getUniqueId(lecturerRepository));
		}
		Lecturer lecturer = Lecturer.of(person);
		if (lecturerRepository.existsById(lecturer.getId())) {
			throw new IllegalStateException("Lecturer with given id already exists" + lecturer.getId());
		}
		PersonDto res = lecturerRepository.save(lecturer).build();
		log.debug("lecturer added {}", res);
		return res;
	}

	@Override
	@Transactional(readOnly = false)
	public SubjectDto addSubject(SubjectDto subject) {
		if (subjectRepository.existsById(subject.getId())) {
			throw new IllegalStateException(String.format("Subject with id %s already exists", subject.getId()));
		}
		Lecturer lecturer = null;
		Long lecturerId = subject.getLecturerId();
		if (lecturerId != null) {
			lecturer = lecturerRepository.findById(lecturerId).orElseThrow(
					() -> new NotFoundException(String.format("Lecturer with id %d doen't exist", lecturerId)));
		}
		Subject subjectEntity = Subject.of(subject);
		subjectEntity.setLecturer(lecturer);
		SubjectDto res = subjectRepository.save(subjectEntity).build();
		log.debug("subject added {}", res);
		return res;
	}

	@Override
	@Transactional(readOnly = false)
	public MarkDto addMark(MarkDto mark) {
		long studentId = mark.getStudent_id();
		Student student = studentRepository.findById(studentId).orElseThrow(
				() -> new NotFoundException(String.format("Student with id %d doesn't exist in DB", studentId)));
		String subjectId = mark.getSubject_id();
		Subject subject = subjectRepository.findById(subjectId).orElseThrow(
				() -> new NotFoundException(String.format("Subject with id %s doesn't exist in DB", subjectId)));
		Mark markEntity = new Mark(student, subject, mark.getMark());
		MarkDto res = markRepository.save(markEntity).build();
		log.debug("subject added {}", res);
		return res;
	}

	private <T> Long getUniqueId(JpaRepository<T, Long> repository) {
		long res;
		do {
			res = new Random().nextLong(minID, maxID + 1);
		} while (repository.existsById(res));
		return res;
	}

	@Override
	public List<IdName> bestStudentsLecturer(long lecturerId, int nStudents) {
		return studentRepository.findBestStudentsLecturer(lecturerId, nStudents);
	}

	@Override
	public List<IdName> studentsAvgMarksGreaterCollege(int nMarksThreshold) {
		return studentRepository.findStudentsAvgMarksGreaterCollege(nMarksThreshold);
	}

	@Override
	public List<IdNameMark> studentsAvgMarks() {
		return studentRepository.findStudentsAvgMarks();
	}

	@Override
	@Transactional(readOnly = false)
	public SubjectDto updateHours(String subjectId, int hours) {
		Subject subject = subjectRepository.findById(subjectId).orElseThrow(
				() -> new NotFoundException(String.format("Subject with id %s doesn't exist in DB", subjectId)));
		subject.setHours(hours);
		return subject.build();
		// не надо делать save, тк есть @Transactional
	}

	@Override
	@Transactional(readOnly = false)
	public SubjectDto updateLecturer(String subjectId, Long lecturerId) {
		Subject subject = subjectRepository.findById(subjectId).orElseThrow(
				() -> new NotFoundException(String.format("Subject with id %s doesn't exist in DB", subjectId)));
		Lecturer lecturer = null;
		if (lecturerId != null) {
			lecturer = lecturerRepository.findById(lecturerId).orElseThrow(
					() -> new NotFoundException(String.format("Lecturer with id %d doesn't exist in DB", lecturerId)));
		}
		subject.setLecturer(lecturer);
		return subject.build();
	}

	@Override
	@Transactional(readOnly = false)
	public List<PersonDto> removeStudentsNoMarks() {
		return removeStudentsLessMarks(1);
	}

	@Override
	@Transactional(readOnly = false)
	public List<PersonDto> removeStudentsLessMarks(int nMarks) {
		List<Student> studentsNoMark = studentRepository.findStudentsLessMark(nMarks);
		studentRepository.removeStudentsLessMark(nMarks);
//		studentsNoMark.forEach(s -> { 
//
//			log.debug("student with id {} is going to be deleted", s.getId());
//			studentRepository.delete(s);
//		});
		return studentsNoMark.stream().map(Student::build).toList();
	}

}
