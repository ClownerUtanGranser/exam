package se.casparsylwan.cugexam.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import se.casparsylwan.cugexam.entity.CugExamUser;
import se.casparsylwan.cugexam.entity.Exam;
import se.casparsylwan.cugexam.entity.ExamTaken;
import se.casparsylwan.cugexam.entity.Question;
import se.casparsylwan.cugexam.exception.PreconditionFailedException;
import se.casparsylwan.cugexam.exception.ResourceAlreadyExsistException;
import se.casparsylwan.cugexam.exception.ResourceNotFoundException;
import se.casparsylwan.cugexam.model.QuestionWithResponse;
import se.casparsylwan.cugexam.repository.CugExamUserRepository;
import se.casparsylwan.cugexam.repository.ExamRepository;
import se.casparsylwan.cugexam.repository.ExamTakenRepository;
import se.casparsylwan.cugexam.repository.QuestionRepository;
import se.casparsylwan.cugexam.responseModel.CugExamUserResponse;
import se.casparsylwan.cugexam.responseModel.CugExamUserWithJWT;
import se.casparsylwan.cugexam.responseModel.CugUserAdminResponse;
import se.casparsylwan.cugexam.security.CugExamUserDetailsService;
import se.casparsylwan.cugexam.security.CugUserDetail;
import se.casparsylwan.cugexam.security.JwtUtil;

import java.util.List;
import java.util.Map;
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

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private ExamTakenRepository examTakenRepository;


    public CugExamUserWithJWT saveUser(CugExamUser cugExamUser) {
        log.info("User recived from CugExamUserService");
        Optional<CugExamUser> cugExamUserOptional = examUserRepository.findByEmail(cugExamUser.getEmail());

        if(cugExamUserOptional.isEmpty())
        {
            log.info("User isEmpty from CugExamUserService");
            cugExamUser = examUserRepository.save(cugExamUser);

            final CugUserDetail userDetails =  cugExamUserDetailsService.loadUserByUsername(cugExamUser.getEmail());
            final String jwt = jwtTokenUtil.generateToken(userDetails);

            CugExamUserWithJWT cugExamUserWithJWT = new CugExamUserWithJWT(cugExamUser, jwt);

            return cugExamUserWithJWT;
        }
        else
        {
            throw new ResourceAlreadyExsistException("Email already exsist take an other email" + cugExamUser.getEmail());
        }
    }

    public List<CugExamUserResponse> updateUser(CugExamUser cugExamUser){
        log.info("update user");
        log.info("User recived from CugExamUserService");
        Optional<CugExamUser> cugExamUserOptional = examUserRepository.findByEmail(cugExamUser.getEmail());

        if(cugExamUserOptional.isEmpty())
        {
            throw new ResourceAlreadyExsistException("Email do not exsist take an other email" + cugExamUser.getEmail());
        }
        else
        {
            if(cugExamUser.getName() == null) cugExamUser.setName(cugExamUserOptional.get().getName());
            if(cugExamUser.getCountry() == null) cugExamUser.setCountry(cugExamUserOptional.get().getCountry());
            if(cugExamUser.getPassword() == null) cugExamUser.setPassword(cugExamUserOptional.get().getPassword());
            cugExamUser.setExamsTaken(cugExamUserOptional.get().getExamsTaken());
            cugExamUser.setRoles(cugExamUserOptional.get().getRoles());

            examUserRepository.save(cugExamUser);
            List<CugExamUser> users = examUserRepository.findAll();

            return users
                    .stream()
                    .map((user) -> new CugExamUserResponse(user))
                    .collect(Collectors.toList());
        }
    }

    public CugUserAdminResponse getUser(String email) {
        log.info("CugExamService getUser " + email);

        Optional<CugExamUser> cugExamUserOptional = examUserRepository.findByEmail(email);
        if(cugExamUserOptional.isPresent())
        {
            CugExamUser cugExamUser = cugExamUserOptional.get();
            log.info("CugExamService getUser: " + cugExamUser.getName());
            return new CugUserAdminResponse(cugExamUser) ;
        }
        else
        {
            throw new PreconditionFailedException("Your session time is out");
        }
    }

    public CugExamUserWithJWT getUserWithJwt(String email) {

        log.info("User recived from CugExamUserService");
        Optional<CugExamUser> cugExamUserOptional = examUserRepository.findByEmail(email);
        if(cugExamUserOptional.isPresent())
        {
            CugExamUser cugExamUser = cugExamUserOptional.get();
            final CugUserDetail userDetails =  cugExamUserDetailsService.loadUserByUsername(cugExamUser.getEmail());
            final String jwt = jwtTokenUtil.generateToken(userDetails);
            return new CugExamUserWithJWT(cugExamUser, jwt);
        }
        else
        {
            throw new PreconditionFailedException("Your session time is out");
        }
    }

    public boolean examCorrection(List<QuestionWithResponse> responseList, String examName) {

        Integer result = 0;
        List<Long> questionIds = responseList
                .stream()
                .map((question) -> question.getQuestionId())
                .collect(Collectors.toList());

        List<Question> correctResponses = questionRepository
                .findAllById(questionIds);

        Integer totalQuestions = responseList.size();
        Integer correct = responseList.stream()
                .filter((question) -> compareQuestions(question, correctResponses))
                .collect(Collectors.toList())
                .size();

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String userEmail = userDetails.getUsername();

        if(correct > 8)
        {
            CugExamUser user = examUserRepository
                    .findByEmail(userEmail).orElseThrow(()-> new ResourceNotFoundException("Could not find user: " + userEmail));
            Exam exam = examRepository.findById(examName)
                    .orElseThrow(()-> new ResourceNotFoundException("Could not find Exam: " + responseList.get(0).getExam().getExamName()));

            ExamTaken examTaken = new ExamTaken( exam, user, true);
            examTakenRepository.save(examTaken);
            return true;
        }
        else
        {
            CugExamUser user = examUserRepository
                    .findByEmail(userEmail).orElseThrow(()-> new ResourceNotFoundException("Could not find user: " + userEmail));
            Exam exam = examRepository.findById(examName)
                    .orElseThrow(()-> new ResourceNotFoundException("Could not find Exam: " + responseList.get(0).getExam().getExamName()));

            ExamTaken examTaken = new ExamTaken( exam, user, false);
            examTakenRepository.save(examTaken);
            return false;
        }
    }

    private boolean compareQuestions(QuestionWithResponse response, List<Question> correctResponses )
    {
        log.info("response.getQuestionId(): " + response.getQuestionId());
        log.info("response.getSelectedAnswre(): " + response.getSelectedAnswre());
        Map<Long, Question> questionMap =
            correctResponses
                .stream()
                .collect(Collectors.toMap(question -> question.getQuestionId(), question -> question));
        if( response.getSelectedAnswre() == null)
        {
            return false;
        }
        else if(questionMap.get(response.getQuestionId()).getCorrectResponse().equals("response1"))
        {
            return response.getSelectedAnswre() == 0;
        }
        else if(questionMap.get(response.getQuestionId()).getCorrectResponse().equals("response2"))
        {
            return response.getSelectedAnswre() == 1;
        }
        else if(questionMap.get(response.getQuestionId()).getCorrectResponse().equals("response3"))
        {
            return response.getSelectedAnswre() == 2;
        }
        else
        {
            return false;
        }
    }
}
