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
    private String question;
    private String lang;

    private String response1;
    private String response2;
    private String response3;
    private String correctResponse;

    @ManyToOne
    @JoinColumn(name = "exam")
    @JsonIgnore
    private Exam exam;
}
