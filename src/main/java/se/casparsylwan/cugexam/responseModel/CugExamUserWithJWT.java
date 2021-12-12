package se.casparsylwan.cugexam.responseModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import se.casparsylwan.cugexam.entity.CugExamUser;

@Data
@AllArgsConstructor
public class CugExamUserWithJWT {

    private CugExamUser cugExamUser;
    private String jwt;

}
