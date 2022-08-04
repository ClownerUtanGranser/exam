package se.casparsylwan.cugexam.responseModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import se.casparsylwan.cugexam.entity.CugExamUser;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CugExamUserResponse {

    private String email;
    private String name;
    private String country;
    private String roles;
    private List<ExamTakenResponse> examsTaken;

    public CugExamUserResponse(CugExamUser cugExamUser){
        email = cugExamUser.getEmail();
        name = cugExamUser.getName();
        country = cugExamUser.getCountry();
        roles = cugExamUser.getRoles();
        examsTaken = cugExamUser.getExamsTaken()
                .stream()
                .map((exam) -> new ExamTakenResponse(exam.getExamName().getExamName(), exam.getId(), exam.isPassed()))
                .collect(Collectors.toList());
    }


}
