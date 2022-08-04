package se.casparsylwan.cugexam.responseModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import se.casparsylwan.cugexam.entity.CugExamUser;

@Data
public class CugExamUserWithJWT {

    private CugExamUserResponse cugExamUser;
    private String jwt;

    public CugExamUserWithJWT(CugExamUser cugExamUser, String jwt){
        this.cugExamUser = new CugExamUserResponse(cugExamUser);
        this.jwt = jwt;
    }

}
