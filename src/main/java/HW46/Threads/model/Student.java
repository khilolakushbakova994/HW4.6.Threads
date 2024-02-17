package HW46.Threads.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="students")
@Entity


public class Student {

    @Id
    @GeneratedValue
    private  Long id;
    @Column
    private  String name;
    @Column
    private int age;


    @ManyToOne
    @JoinColumn(name = "faculty_id")
    @Getter
    @JsonIgnore
    private Faculty faculty;
}
