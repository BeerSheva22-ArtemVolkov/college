package telran.spring.college.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.spring.college.dto.IdName;
import telran.spring.college.dto.IdNameMark;
import telran.spring.college.dto.MarkDto;
import telran.spring.college.dto.PersonDto;
import telran.spring.college.dto.QueryDto;
import telran.spring.college.dto.ResponseDto;
import telran.spring.college.dto.SubjectDto;
import telran.spring.college.service.CollegeService;

@RestController
@RequestMapping("college")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class CollegeController {

	final CollegeService collegeService;

	@PostMapping("/students")
	public PersonDto addStudent(@RequestBody PersonDto person) {

		return collegeService.addStudent(person);
	}

	@PostMapping("/lecturers")
	public PersonDto addLecturer(@RequestBody PersonDto person) {
		return collegeService.addLecturer(person);
	}

	@PostMapping("/subjects")
	public SubjectDto addSubject(@RequestBody SubjectDto subject) {

		return collegeService.addSubject(subject);
	}

	@PostMapping("/marks")
	public MarkDto addMark(@RequestBody MarkDto mark) {

		return collegeService.addMark(mark);
	}

	@GetMapping("/students/lecturer/best")
	public List<IdName> bestStudentsLecturer(@RequestParam long lecturerId,
			@RequestParam(defaultValue = "3") int nStudents) {

		return collegeService.bestStudentsLecturer(lecturerId, nStudents);
	}

	@GetMapping("/students/best")
	public List<IdName> studentsAvgMarksGreaterCollegeAvg(
			@RequestParam(name = "nMarks", defaultValue = "1") int nMarksThreshold) {

		return collegeService.studentsAvgMarksGreaterCollege(nMarksThreshold);
	}

	@GetMapping("/students/marks")
	public List<IdNameMark> studentsAvgMarks() {

		return collegeService.studentsAvgMarks();
	}

	@PutMapping("/subjects/hours/{subjectId}")
	public SubjectDto updateHours(@PathVariable String subjectId, @RequestParam int hours) {
		SubjectDto res = collegeService.updateHours(subjectId, hours);
		log.error(String.format("%d", res.getLecturerId()));
		return res;
	}

	@PutMapping("/subjects/lecturer/{subjectId}")
	public SubjectDto updateLecturer(@PathVariable String subjectId, @RequestParam Long lecturerId) {
		return collegeService.updateLecturer(subjectId, lecturerId);
	}

	@DeleteMapping("/students")
	public List<PersonDto> removeStudentsMarks(@RequestParam(defaultValue = "1") int nMarks) {

		return nMarks < 2 ? collegeService.removeStudentsNoMarks() : collegeService.removeStudentsLessMarks(nMarks);
	}

	@DeleteMapping("/lecturers/{lecturerId}")
	public PersonDto removeLecturer(@PathVariable long lecturerId) {

		return collegeService.removeLecturer(lecturerId);
	}

	@PostMapping("jpql")
	ResponseEntity<ResponseDto> jpqlProcess(@RequestBody QueryDto query) {
		String requestText = query.getQuery();
		String method = query.getMethod();
		Integer limit = query.getLimit();
		log.error("Query accepted: <{}>: <{} limit {}>", method, requestText, limit);
		try {
			ResponseDto res = collegeService.jpqlQuery(query);
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseDto(new ArrayList<String>(), "", e.getMessage()));
		}
	}

}
