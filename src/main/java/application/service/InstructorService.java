package application.service;
import application.model.Instructor;
import application.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InstructorService {
    @Autowired
    private InstructorRepository instructorRepository;

    public void createInstructor(Instructor instructor) {
        instructorRepository.save(instructor);
    }

    public Instructor findByAccountId(int accountId) {
        return instructorRepository.findByAccountId(accountId);
    }

    public List<Instructor> findByCourseId(int courseId) {
        return instructorRepository.findByCourseId(courseId);
    }

}
