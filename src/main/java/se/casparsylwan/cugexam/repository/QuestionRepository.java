package se.casparsylwan.cugexam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.casparsylwan.cugexam.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
