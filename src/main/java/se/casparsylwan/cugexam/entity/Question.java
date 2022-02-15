package se.casparsylwan.cugexam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Question {

    @Id
    private Long questionId;
    private Long questionNumber;
//    @Column(columnDefinition="LONGTEXT")
    @Column(length = 500)
    private String question;
    private String lang;

    @Column(columnDefinition="TEXT")
    private String response1;
    @Column(columnDefinition="TEXT")
    private String response2;
    @Column(columnDefinition="TEXT")
    private String response3;

    private String correctResponse;

    @ManyToOne
    @JoinColumn(name = "exam")
    private Exam exam;
}
