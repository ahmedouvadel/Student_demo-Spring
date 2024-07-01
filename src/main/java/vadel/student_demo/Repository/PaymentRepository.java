package vadel.student_demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vadel.student_demo.Entity.Payment;
import vadel.student_demo.Entity.Student;
import vadel.student_demo.Enum.PaymentStatus;
import vadel.student_demo.Enum.PaymentType;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    List<Payment> findByStudentCode(String code);
    List<Payment> findByPaymentStatus(PaymentStatus status); //yalla dir "findByStatus"
    List<Payment> findByPaymentType(PaymentType type);  //yalla dir "findByType"
}
