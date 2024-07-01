package vadel.student_demo.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vadel.student_demo.Entity.Student;
import vadel.student_demo.Repository.PaymentRepository;
import vadel.student_demo.Repository.StudentRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/student")
public class StudentController {

    private final StudentRepository studentRepository;
    private final PaymentRepository paymentRepository;

    @GetMapping
    public List<Student> getAllStudent(){
        return studentRepository.findAll();
    }

    @GetMapping(path = "code/{code}")
    public Student getStudentByCode(@PathVariable String code){
        return studentRepository.findStudentByCode(code);
    }

    @GetMapping(path = "program/{programId}")
    public List<Student> getStudentByProgramId(@PathVariable String programId){
        return studentRepository.findByProgramId(programId);
    }

}
