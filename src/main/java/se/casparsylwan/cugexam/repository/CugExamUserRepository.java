package se.casparsylwan.cugexam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.casparsylwan.cugexam.entity.CugExamUser;

import java.util.Optional;

public interface CugExamUserRepository extends JpaRepository<CugExamUser, Long> {
    Optional<CugExamUser> findByEmail(String userName);
}
