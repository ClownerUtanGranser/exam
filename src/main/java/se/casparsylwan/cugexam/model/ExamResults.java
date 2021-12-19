package se.casparsylwan.cugexam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExamResults {

   private String examName;
   private Integer numberOfQuestions;
   private Integer correctResponsen;
   private Integer wrongResponses;
   private Integer noCorrectResponses;


}
