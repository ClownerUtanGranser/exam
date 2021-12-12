package se.casparsylwan.cugexam.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import se.casparsylwan.cugexam.entity.CugExamUser;
import se.casparsylwan.cugexam.entity.Question;
import se.casparsylwan.cugexam.exception.PreconditionFailedException;
import se.casparsylwan.cugexam.exception.ResourceAlreadyExsistException;
import se.casparsylwan.cugexam.repository.CugExamUserRepository;
import se.casparsylwan.cugexam.repository.QuestionRepository;
import se.casparsylwan.cugexam.responseModel.CugExamUserWithJWT;
import se.casparsylwan.cugexam.security.CugExamUserDetailsService;
import se.casparsylwan.cugexam.security.JwtUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CugExamUserService {

    @Autowired
    private CugExamUserRepository examUserRepository;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private CugExamUserDetailsService cugExamUserDetailsService;

    @Autowired
    private QuestionRepository questionRepository;


    public CugExamUserWithJWT saveUser(CugExamUser cugExamUser) {
        log.info("User recived from CugExamUserService");
        Optional<CugExamUser> cugExamUserOptional = examUserRepository.findByEmail(cugExamUser.getEmail());

        if(cugExamUserOptional.isEmpty())
        {
            log.info("User isEmpty from CugExamUserService");
            cugExamUser = examUserRepository.save(cugExamUser);

            final UserDetails userDetails =  cugExamUserDetailsService.loadUserByUsername(cugExamUser.getEmail());
            final String jwt = jwtTokenUtil.generateToken(userDetails);

            CugExamUserWithJWT cugExamUserWithJWT = new CugExamUserWithJWT(cugExamUser, jwt);

            return cugExamUserWithJWT;
        }
        else
        {
            throw new ResourceAlreadyExsistException("Email already exsist take an other email" + cugExamUser.getEmail());
        }
    }

    public Optional<CugExamUser> getUser(String email) {
        log.info("CugExamService getUser " + email);
        return examUserRepository.findByEmail(email);
    }

    public CugExamUserWithJWT getUserWithJwt(String email) {

        log.info("User recived from CugExamUserService");
        Optional<CugExamUser> cugExamUserOptional = examUserRepository.findByEmail(email);
        if(cugExamUserOptional.isPresent())
        {
            CugExamUser cugExamUser = cugExamUserOptional.get();
            final UserDetails userDetails =  cugExamUserDetailsService.loadUserByUsername(cugExamUser.getEmail());
            final String jwt = jwtTokenUtil.generateToken(userDetails);
            return new CugExamUserWithJWT(cugExamUser, jwt);
        }
        else
        {
            throw new PreconditionFailedException("Your session time is out");
        }
    }

    public List<Question> examCorrection(List<Question> responseList) {

        Integer result = 0;
        List<Long> questionIds = responseList.stream().
                map((question) -> question.getQuestionId()).
                collect(Collectors.toList());
        List<Question> correctResponses = questionRepository.findAllById(questionIds);

        return responseList;
    }
}
