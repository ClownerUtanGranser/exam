package se.casparsylwan.cugexam.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.casparsylwan.cugexam.entity.CugExamUser;
import se.casparsylwan.cugexam.entity.Exam;
import se.casparsylwan.cugexam.entity.Question;
import se.casparsylwan.cugexam.exception.ResourceAlreadyExsistException;
import se.casparsylwan.cugexam.exception.ResourceNotFoundException;
import se.casparsylwan.cugexam.repository.CugExamUserRepository;
import se.casparsylwan.cugexam.repository.ExamRepository;
import se.casparsylwan.cugexam.repository.QuestionRepository;
import se.casparsylwan.cugexam.responseModel.CugExamUserResponse;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AdminService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CugExamUserRepository cugExamUserRepository;

    public Exam createExam(Exam exam)
    {
        log.info("AdminService save exam " + exam.getExamName());
        Optional<Exam> optionalExam = examRepository.findById(exam.getExamName());

        if(optionalExam.isEmpty())
        {
            exam = examRepository.save(exam);
        }
        else
        {
            log.info("AdminService save already excist " + exam.getExamName());
            throw new ResourceAlreadyExsistException("Exam already excits" + exam.getExamName());
        }
        return exam;
    }

    public Exam createExamWithQuestions(Exam exam)
    {
        log.info("AdminService save exam " + exam.getExamName());
        exam = examRepository.save(exam);
        log.info("AdminService save questions " + exam.getQuestions().size());
        questionRepository.saveAll(exam.getQuestions());
        return exam;
    }

    public Exam addQuestionsToExam(String examName, List<Question> questions)
    {
        log.info("AdminService addQuestionsToExam(String examName)");
        Exam exam = examRepository.findById(examName).orElseThrow(() -> new ResourceNotFoundException("Could not find exam " + examName));
        log.info("AdminService add exam on questions");
        questions.forEach((question) -> question.setExam(exam));
        questionRepository.saveAll(questions);

        return exam ;
    }

    public Optional<Exam> getExamByName(String examName)
    {
        return examRepository.findById(examName);
    }

    public void deleteQuestion(Long questionId)
    {
        questionRepository.deleteById(questionId);
    }

    public Exam updateExamQuestion(String examName, Question question)
    {
        Exam exam = examRepository.findById(examName).orElseThrow(() -> new ResourceNotFoundException("Could not find exam " + examName));
        question.setExam(exam);
        log.info("Log question: " + question.getQuestion());
        questionRepository.save(question);

        return exam;
    }

    public List<CugExamUserResponse> getAllExamUsers()
    {
        log.info("getAllExamUsers()");
        List<CugExamUser> users = cugExamUserRepository.findAll();
        return users
                .stream()
                .map((user) -> new CugExamUserResponse(user))
                .collect(Collectors.toList());
    }

    public ByteArrayInputStream getAllExamUsersAsExcel()
    {
        log.info("Get All users to Excel");
        List<CugExamUser> users = cugExamUserRepository.findAll();
        ByteArrayInputStream in = ExcelHelper.cugExamUsersToExcel(users);
        return in;
    }
}
