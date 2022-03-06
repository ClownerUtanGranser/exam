package se.casparsylwan.cugexam.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.casparsylwan.cugexam.entity.CugExamUser;
import se.casparsylwan.cugexam.entity.Exam;
import se.casparsylwan.cugexam.entity.Question;
import se.casparsylwan.cugexam.exception.ResourceNotFoundException;
import se.casparsylwan.cugexam.service.AdminService;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@RestController
@RequestMapping("v1/admin")
@Slf4j
public class AdminController {

    @Autowired
    private AdminService adminService;



    @PostMapping("create/exam")
    public Exam createExam(@RequestBody Exam exam)
    {
        log.info("Exam created in AdminController ");
        Exam savedExam = adminService.createExam(exam);
        return savedExam;
    }

    @PostMapping("create/examandquestions")
    public Exam createExamWithQuestions(@RequestBody Exam exam)
    {
        log.info("Exam created in AdminController ");
        Exam savedExam = adminService.createExamWithQuestions(exam);
        return savedExam;
    }

    @PutMapping("exam/question/{examName}")
    public Exam updateExamQuetion(@PathVariable String examName, @RequestBody Question question)
    {
        log.info("Update exam: " + examName + "with question: " + question.getQuestionId());
        return adminService.updateExamQuestion(examName, question);
    }

    @PostMapping("exam/question/{examName}")
    public Exam addQuestionToExam(@PathVariable String examName, @RequestBody List<Question> questions)
    {
        log.info("Add question to exam " + examName + " questions added " + questions.size());
        return adminService.addQuestionsToExam(examName, questions);

    }

    @DeleteMapping("exam/delete/question")
    public void deleteQuestion(@RequestBody Long questionId)
    {
        log.info("Delete question " + questionId);
        adminService.deleteQuestion(questionId);
    }

    @GetMapping("exam/{examName}")
    public Exam getExamByName(@PathVariable String examName)
    {
        log.info("getExamByName " + examName);
        Exam exam = adminService.getExamByName(examName).orElseThrow(() -> new ResourceNotFoundException("Could not find exam " + examName));
        exam.getQuestions().sort((a, b) -> {
            if(a.getQuestionNumber() == null)
            {
                return -1;
            }
            else if(b.getQuestionNumber() == null)
            {
                return -1;
            }
            else if(a.getQuestionNumber()<b.getQuestionNumber())
            {
                return -1;
            }
            else
            {
                return 1;
            }

        });
        return exam;
    }

    @GetMapping("users/all")
    public List<CugExamUser> getAllExamUsers()
    {
        log.info("Get all users");
        return adminService.getAllExamUsers();
    }

    @PostMapping("users/all/excel")
    public ResponseEntity<Resource> getAllExamUsersExcel()
    {
        log.info("Get all users");
        String filename = "examtakers.xlsx";
        InputStreamResource file = new InputStreamResource(adminService.getAllExamUsersAsExcel());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename + ".xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }
}
