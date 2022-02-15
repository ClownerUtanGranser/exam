package se.casparsylwan.cugexam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String question;
    private String lang;

//    @Column(columnDefinition="LONGTEXT")
    private String response1;
//    @Column(columnDefinition="LONGTEXT")
    private String response2;
//    @Column(columnDefinition="LONGTEXT")
    private String response3;
//    @Column(columnDefinition="LONGTEXT")
    private String correctResponse;

    @ManyToOne
    @JoinColumn(name = "exam")
    private Exam exam;
}
