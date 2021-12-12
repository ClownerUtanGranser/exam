package se.casparsylwan.cugexam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.casparsylwan.cugexam.entity.ExamTaken;

public interface ExamTakenRepository extends JpaRepository<ExamTaken, Long> {
}
