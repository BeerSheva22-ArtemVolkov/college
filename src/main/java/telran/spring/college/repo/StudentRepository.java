package telran.spring.college.repo;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import telran.spring.college.dto.IdName;
import telran.spring.college.dto.IdNameMark;
import telran.spring.college.dto.SubjectType;
import telran.spring.college.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

	// Исходя из IdName:
	// sl.id as id должно быть long
	// sl.name as name должно быть String
	@Query(value = "select sl.id as id, sl.name as name "
		      + "from (select * from students_lecturers where dtype='Student') sl "
		      + "join marks on sl.id=student_id "
		      + "join subjects sbj on subject_id=sbj.id "
		      + "where lecturer_id=:lecturerId "
		      + "group by sl.id "
		      + "order by avg(mark) desc "
		      + "limit :nStudents", nativeQuery = true)
	List<IdName> findBestStudentsLecturer(@Param(value = "lecturerId") long lecturerId,
			@Param(value = "nStudents") int nStudents);

	@Query(	"select student.id as id, student.name as name "
			+ "from Mark "
			+ "group by student.id "
			+ "having count(mark) > :nMarks "
			+ "and avg(mark) > ("
				+ "select avg(mark) "
				+ "from Mark"
			+ ") "
			+ "order by avg(mark) desc")
	List<IdName> findStudentsAvgMarksGreaterCollege(@Param(value = "nMarks") int nMarks);

	@Query(value = "select sl.id as id, sl.name as name, coalesce(round(avg(m.mark), 1), 0) as mark"
			+ " from (select * from students_lecturers where dtype='Student') sl"
			+ " left join marks m on sl.id=student_id"
			+ " group by sl.id"
			+ " order by avg(mark) desc", nativeQuery = true)
	List<IdNameMark> findStudentsAvgMarks();

	// Берется класс Student 
	// student - объект класса Student
	@Query(	"select student "
			+ "from Student student "
			+ "where size(marks) < :nMarks")
	List<Student> findStudentsLessMark(int nMarks);
	
	@Modifying
	@Query(	"delete "
			+ "from Student "
			+ "where size(marks) < :nMarks")
	void removeStudentsLessMark(int nMarks);
	
	List<IdName> findDistinctByMarksSubjectTypeAndMarksMarkGreaterThanOrderById(SubjectType type, int mark);
	
}
