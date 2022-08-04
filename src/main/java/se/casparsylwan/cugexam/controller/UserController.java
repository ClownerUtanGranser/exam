package se.casparsylwan.cugexam.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.casparsylwan.cugexam.entity.CugExamUser;
import se.casparsylwan.cugexam.exception.PreconditionFailedException;
import se.casparsylwan.cugexam.model.QuestionWithResponse;
import se.casparsylwan.cugexam.responseModel.CugExamUserResponse;
import se.casparsylwan.cugexam.responseModel.CugExamUserWithJWT;
import se.casparsylwan.cugexam.responseModel.CugUserAdminResponse;
import se.casparsylwan.cugexam.service.CugExamUserService;

import java.util.List;

@RestController
@RequestMapping("v1/user")
@Slf4j
public class UserController {

    @Autowired
    private CugExamUserService cugExamUserService;

    @PostMapping("/create")
    public CugExamUserWithJWT createUser(@RequestBody CugExamUser cugExamUser){
        log.info("UserController create user", cugExamUser);
        if(cugExamUser.getEmail().isEmpty() || cugExamUser.getPassword().isEmpty())
        {
            throw new PreconditionFailedException("Email or password is empty!");
        }

        CugExamUserWithJWT cugExamUserWithJWT = cugExamUserService.saveUser(cugExamUser);

        return cugExamUserWithJWT;
    }

    @PostMapping("/update")
    public List<CugExamUserResponse> updateUser(@RequestBody CugExamUser cugExamUser){
        log.info("CugExamUser update " + cugExamUser.getEmail());
        return cugExamUserService.updateUser(cugExamUser);
    }

    @GetMapping("/{email}")
    public ResponseEntity<CugUserAdminResponse> getUser(@PathVariable String email)
    {
        log.info("controller GetUser Entity: " + email);
        CugUserAdminResponse cugExamUser = cugExamUserService.getUser(email);
        log.info("GetUser " + cugExamUser.getEmail());
        return ResponseEntity.ok(cugExamUser);
    }

    @GetMapping("/jwt/{email}")
    public CugExamUserWithJWT getUserWithJwt(@PathVariable String email)
    {
        log.info("controller GetUser " + email);
        CugExamUserWithJWT cugExamUserWithJWT = cugExamUserService.getUserWithJwt(email);
        log.info("Conroller jwt with " + email);
        return cugExamUserWithJWT;
    }

    @PostMapping("exam/correction/{examName}")
    public boolean examCorrection(@PathVariable String examName, @RequestBody List<QuestionWithResponse> responseList)
    {
        log.info("Correction of exam: " + examName);
        responseList.forEach((res) -> System.out.println(res.getSelectedAnswre()));
        //cugExamUserService.examCorrection(responseList);
        return cugExamUserService.examCorrection(responseList, examName);
    }
}
