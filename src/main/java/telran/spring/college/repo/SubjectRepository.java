package telran.spring.college.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.spring.college.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, String> {

}
