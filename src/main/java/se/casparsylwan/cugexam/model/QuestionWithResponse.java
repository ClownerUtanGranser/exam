package se.casparsylwan.cugexam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.casparsylwan.cugexam.entity.Question;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionWithResponse extends Question {
    private Integer selectedAnswre;
}
