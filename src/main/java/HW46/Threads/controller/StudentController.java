package HW46.Threads.controller;


import HW46.Threads.model.Student;
import HW46.Threads.service.AvatarService;
import HW46.Threads.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;
    private final AvatarService avatarService;

    public StudentController(StudentService studentService, AvatarService avatarService) {
        this.studentService = studentService;
        this.avatarService = avatarService;
    }


    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> find(@PathVariable Long id) {
        Student student = studentService.find(id);
        if (student == null) {
            return ResponseEntity.notFound().build();

        }
        return ResponseEntity.ok(student);

    }

    @PutMapping
    public ResponseEntity<Student> changeStudentInfo(@RequestBody Student student) {
        Student changeStudent = studentService.changeStudentInfo(student);
        if (changeStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(changeStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> removeStudent(@PathVariable Long id) {
        studentService.removeStudent(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/all-students")
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/search-age/{age}")
    public Collection<Student> findStudentByAge(@PathVariable Integer age) {
        return studentService.findStudentByAge(age);
    }

    @GetMapping("/sort-age")
    public Collection<Student> findByAgeBetween(@RequestParam int min, @RequestParam int max) {
        return studentService.findByAgeBetween(min, max);

    }

    @GetMapping("/starts-with-A")
    public ResponseEntity<List<String>> getStudentsStartingWithA() {
        return ResponseEntity.ok(studentService.getStudentsStartingWithA());
    }

    @GetMapping("/average-age")
    public ResponseEntity<Double> getAverageAge() {
        return ResponseEntity.ok(studentService.getAverageAge());
    }

    @GetMapping("/returnNumber")
    public ResponseEntity<Integer> returnNumber() {
        return ResponseEntity.ok(studentService.returnNumber());
    }

    @GetMapping("/print-parallel")
    public void printStudentNamesParallel() throws InterruptedException {
        studentService.printStudentNamesParallel();
    }

    @GetMapping("/print-synchronized")
    public String printSynchronized() {
        studentService.synchronizedPrint();
        return "Names printed synchronously";
    }
}

