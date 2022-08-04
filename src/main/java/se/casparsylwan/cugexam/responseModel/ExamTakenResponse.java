package se.casparsylwan.cugexam.responseModel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExamTakenResponse {

    private String examName;
    private Long id;
    private boolean passed;

}
