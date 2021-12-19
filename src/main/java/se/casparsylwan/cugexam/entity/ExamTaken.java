package se.casparsylwan.cugexam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExamTaken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Timestamp start;
    private Timestamp end;

    @ManyToOne()
    @JsonIgnoreProperties({"questions"})
    private Exam examName;

    @ManyToOne
    @JoinColumn(name = "exam")
    @JsonIgnore()
    private CugExamUser examTaker;

    private boolean passed;

    public ExamTaken(Timestamp end, Exam examName, CugExamUser examTaker, boolean passed)
    {
        this.end = end;
        this.examName = examName;
        this.examTaker = examTaker;
        this.passed = passed;
    }

}
