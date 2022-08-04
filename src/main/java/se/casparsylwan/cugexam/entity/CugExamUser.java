package se.casparsylwan.cugexam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CugExamUser {

    @Id
    private String email;
    private String name;
    private String country;
    private String password;
    private String roles;

    @OneToMany(mappedBy = "examTaker")
    private List<ExamTaken> examsTaken;



}
