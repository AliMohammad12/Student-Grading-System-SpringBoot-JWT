package application.service;

import application.model.Instructor;
import application.model.Student;
import application.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public void createStudent(Student student) {
        studentRepository.save(student);
    }
    public Student findByAccountId(int accountId) {
        return studentRepository.findByAccountId(accountId);
    }
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    public Student findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }
}
