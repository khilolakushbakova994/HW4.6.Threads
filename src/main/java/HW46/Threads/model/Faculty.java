package HW46.Threads.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "faculties")


public class Faculty {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;
    @Column
    private String color;
    @JsonIgnore
    @OneToMany(mappedBy = "faculty")
    private List<Student> students;


}
