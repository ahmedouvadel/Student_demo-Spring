package vadel.student_demo.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vadel.student_demo.Entity.Payment;
import vadel.student_demo.Entity.Student;
import vadel.student_demo.Enum.PaymentStatus;
import vadel.student_demo.Enum.PaymentType;
import vadel.student_demo.Repository.PaymentRepository;
import vadel.student_demo.Repository.StudentRepository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
    @PutMapping(path = "save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Payment savePayment(@RequestParam MultipartFile multipartFile, LocalDate date,double amount,PaymentType type,String studentCode) throws IOException {
        String path = System.getProperty("user.home") + "/OneDrive/Desktop/Formation  2024/Back-End/Student_demo";

        Path pathFolder = Paths.get(path, "file-data", "payments");
        if (!Files.exists(pathFolder)) {
            Files.createDirectories(pathFolder);
        }

        String fileName = UUID.randomUUID().toString();
        Path filePath = pathFolder.resolve(fileName + ".pdf");

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            // Handle file copying exception
            e.printStackTrace(); // Replace with proper error handling
        }
        Student student= studentRepository.findStudentByCode(studentCode);
        Payment payment = Payment
                .builder()
                .date(date)
                .student(student)
                .amount(amount)
                .paymentType(type)
                .paymentStatus(PaymentStatus.CREATED)
                .file(filePath.toUri().toString())
                .build();
        return paymentRepository.save(payment);
    }
    @GetMapping(path = "file/{paymentId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] getPaymentFile(@PathVariable Long paymentId) throws IOException {
        Payment payment = paymentRepository.findById(paymentId).get();
        return Files.readAllBytes(Path.of(URI.create(payment.getFile())));
    }

}
