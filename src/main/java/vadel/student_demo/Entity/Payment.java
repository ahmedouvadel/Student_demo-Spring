package vadel.student_demo.Entity;

import jakarta.persistence.*;
import lombok.*;
import vadel.student_demo.Enum.PaymentStatus;
import vadel.student_demo.Enum.PaymentType;

import java.time.LocalDate;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder @ToString
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private double amount;
    private PaymentType paymentType;
    private PaymentStatus paymentStatus;
    private String file;
    @ManyToOne
    private Student student;

}
