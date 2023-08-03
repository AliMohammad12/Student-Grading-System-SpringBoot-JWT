package application.service;

import application.model.Instructor;
import application.model.Student;
import application.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
