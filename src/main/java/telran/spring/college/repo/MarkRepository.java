package telran.spring.college.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.spring.college.entity.Mark;

public interface MarkRepository extends JpaRepository<Mark, Integer> {

	List<Mark> findByStudentIdAndSubjectId(long studentId, String subjectId);

//	@Query(value = "select * from marks " 
//			+ "where student_id=:studentId", nativeQuery = true)
//	List<Mark> findMarkByStudentId(long studentId);
}
