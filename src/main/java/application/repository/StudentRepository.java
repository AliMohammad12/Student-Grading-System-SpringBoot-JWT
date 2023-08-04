package application.repository;

import application.model.Instructor;
import application.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findByAccountId(int accountId);
    Student findByEmail(String email);
}
