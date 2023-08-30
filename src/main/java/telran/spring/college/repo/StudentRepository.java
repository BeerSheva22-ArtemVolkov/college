package telran.spring.college.repo;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import telran.spring.college.dto.IdName;
import telran.spring.college.dto.IdNameMark;
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

	@Query(value = "select sl.id as id, sl.name as name "
			+ "from (select * from students_lecturers where dtype='Student') sl "
			+ "join marks on sl.id=student_id "
			+ "group by sl.id "
			+ "having count(mark) >= :nMarksThreshold "
			+ "and avg(mark) > (select avg(mark) from marks)" 
			+ "order by avg(mark) desc", nativeQuery = true)
	List<IdName> findStudentsAvgMarksGreaterCollege(@Param(value = "nMarksThreshold") int nMarksThreshold);

	@Query(value = "select sl.id as id, sl.name as name, coalesce(round(avg(m.mark), 1), 0) as mark"
			+ " from (select * from students_lecturers where dtype='Student') sl"
			+ " left join marks m on sl.id=student_id"
			+ " group by sl.id"
			+ " order by avg(mark) desc", nativeQuery = true)
	List<IdNameMark> findStudentsAvgMarks();

	@Query(value = "select * "
			+ "from students_lecturers "
			+ "where dtype = 'Student' and id in ("
				+ "select sl.id from students_lecturers sl "
				+ "left join marks on sl.id=student_id "
				+ "group by sl.id "
				+ "having count(mark) < :nMarks"
			+ ")", nativeQuery = true)
	List<Student> findStudentsLessMark(int nMarks);
	
	@Modifying
	@Query(value = "delete "
			+ "from students_lecturers "
			+ "where dtype = 'Student' and id in ("
				+ "select sl.id from students_lecturers sl "
				+ "left join marks on sl.id=student_id "
				+ "group by sl.id "
				+ "having count(mark) < :nMarks"
			+ ")", nativeQuery = true)
	void removeStudentsLessMark(int nMarks);
	
	
}
