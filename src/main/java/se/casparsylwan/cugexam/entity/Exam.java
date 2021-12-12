package se.casparsylwan.cugexam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Exam {

    @Id
    private String examName;

    @OneToMany(
            mappedBy = "exam"
    )
    private List<Question> questions;

    @ManyToOne
    @JsonIgnore
    private ExamTaken examTaken;
}
