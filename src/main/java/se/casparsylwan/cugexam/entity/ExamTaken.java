package se.casparsylwan.cugexam.entity;

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

    @ManyToOne
    private Exam examName;

    @ManyToOne
    private CugExamUser examTaker;

}
