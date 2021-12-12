package se.casparsylwan.cugexam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.casparsylwan.cugexam.entity.Exam;

public interface ExamRepository extends JpaRepository<Exam, String> {

}
