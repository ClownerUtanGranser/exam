package se.casparsylwan.cugexam.responseModel;

import lombok.Data;
import se.casparsylwan.cugexam.entity.CugExamUser;
import se.casparsylwan.cugexam.entity.ExamTaken;

import javax.persistence.Id;

import java.util.List;
import java.util.stream.Collectors;
@Data
public class CugUserAdminResponse {

    private String email;
    private String name;
    private String country;
    private String password;
    private String roles;

    private List<ExamTakenResponse> examsTaken;

    public CugUserAdminResponse(CugExamUser cugExamUser){
        email = cugExamUser.getEmail();
        name = cugExamUser.getName();
        country = cugExamUser.getCountry();
        password = cugExamUser.getPassword();
        roles = cugExamUser.getRoles();
        examsTaken = cugExamUser
                .getExamsTaken()
                .stream()
                .map((exam) -> new ExamTakenResponse(exam.getExamName().getExamName(), exam.getId(), exam.isPassed()))
                .collect(Collectors.toList());
    }
}
