package vadel.student_demo;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vadel.student_demo.Entity.Payment;
import vadel.student_demo.Entity.Student;
import vadel.student_demo.Enum.PaymentStatus;
import vadel.student_demo.Enum.PaymentType;
import vadel.student_demo.Repository.PaymentRepository;
import vadel.student_demo.Repository.StudentRepository;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
@RequiredArgsConstructor
public class StudentDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentDemoApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository,
                                        PaymentRepository paymentRepository){
        return args -> {
          //  Long aLong = Long.valueOf(UUID.randomUUID().toString());
            studentRepository.save(Student.builder()
                            .id(1L)
                            .firstName("Fatma").lastName("Mama").code("445566").programId("EPI")
                    .build());

            studentRepository.save(Student.builder()
                    .id(2L)
                    .firstName("Ahmed").lastName("Vadel").code("112233").programId("EPI")
                    .build());

            studentRepository.save(Student.builder()
                    .id(3L)
                    .firstName("Hassen").lastName("Ali").code("334455").programId("EPI")
                    .build());

            PaymentType[] paymentTypes = PaymentType.values();
            Random random = new Random();
            studentRepository.findAll().forEach(student -> {
                for (int i=0; i<10 ; i++);
                int index = random.nextInt(paymentTypes.length);
                Payment payment = Payment.builder()
                        .amount(1000+(int)(Math.random()+2000))
                        .paymentStatus(PaymentStatus.CREATED)
                        .paymentType(paymentTypes[index])
                        .date(LocalDate.now())
                        .student(student)
                        .build();
                paymentRepository.save(payment);
            });
        };
    }

}
