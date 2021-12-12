package se.casparsylwan.cugexam.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.casparsylwan.cugexam.entity.CugExamUser;
import se.casparsylwan.cugexam.entity.Question;
import se.casparsylwan.cugexam.exception.PreconditionFailedException;
import se.casparsylwan.cugexam.exception.ResourceNotFoundException;
import se.casparsylwan.cugexam.responseModel.CugExamUserWithJWT;
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

    @GetMapping("/{email}")
    public ResponseEntity<CugExamUser> getUser(@PathVariable String email)
    {
        log.info("controller GetUser " + email);
        CugExamUser cugExamUser = cugExamUserService.getUser(email).orElseThrow(() -> new ResourceNotFoundException("Customer with email could not be found: " + email));
        log.info("GetUser " + cugExamUser);
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

    @PostMapping("exam/correction")
    public List<Question> examCorrection(@RequestBody List<Question> responseList)
    {
        log.info("Correction of exams");
        cugExamUserService.examCorrection(responseList);
        return cugExamUserService.examCorrection(responseList);
    }
}
