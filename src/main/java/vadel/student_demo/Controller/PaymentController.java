package vadel.student_demo.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vadel.student_demo.Entity.Payment;
import vadel.student_demo.Enum.PaymentStatus;
import vadel.student_demo.Enum.PaymentType;
import vadel.student_demo.Repository.PaymentRepository;
import vadel.student_demo.Repository.StudentRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/pay")
public class PaymentController {

    private final StudentRepository studentRepository;
    private final PaymentRepository paymentRepository;

    @GetMapping
    public List<Payment> getAllPayment(){
        return paymentRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Payment getPaymentById(@PathVariable Long id){
        return paymentRepository.findById(id).orElse(null);
    }

    @GetMapping(path = "code/{code}")
    public List<Payment> getPaymentByCode(@PathVariable String code){
        return paymentRepository.findByStudentCode(code);
    }

    @GetMapping(path = "status/{status}")
    public List<Payment> getPaymentByStatus(@PathVariable PaymentStatus status){
        return paymentRepository.findByPaymentStatus(status);
    }

    @GetMapping(path = "type/{type}")
    public List<Payment> getPaymentByStatus(@PathVariable PaymentType type){
        return paymentRepository.findByPaymentType(type);
    }

    @PutMapping(path = "status/{id}")
    public Payment updatePaymentStatus( @RequestParam PaymentStatus status,@PathVariable Long id ){
        Payment payment = paymentRepository.findById(id).get();
        payment.setPaymentStatus(status);
        return paymentRepository.save(payment);
    }

}
