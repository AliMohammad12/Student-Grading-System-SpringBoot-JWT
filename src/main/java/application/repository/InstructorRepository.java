package application.repository;

import application.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InstructorRepository extends JpaRepository<Instructor, Integer> {
    Instructor findByAccountId(int accountId);
    Instructor findByEmail(String email);
    @Query("SELECT i FROM Instructor i INNER JOIN i.instructorCourses ic WHERE ic.course.id = :courseId")
    List<Instructor> findByCourseId(@Param("courseId") int courseId);

}