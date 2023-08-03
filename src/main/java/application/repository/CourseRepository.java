package application.repository;

import application.model.Course;
import application.model.Instructor;
import application.model.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Query("SELECT c FROM Course c JOIN StudentCourse sc ON c.id = sc.course.id WHERE sc.student.id = :studentID")
    List<Course> findCoursesByStudentID(@Param("studentID") int studentID);

    @Query("SELECT c FROM Course c JOIN c.instructors i WHERE i.id = :instructorId")
    List<Course> findCoursesByInstructorId(@Param("instructorId") int instructorId);

    @Query("SELECT sc FROM StudentCourse sc WHERE sc.student.id = :studentId AND sc.course.id = :courseId")
    StudentCourse findStudentCourseByStudentIdAndCourseId(@Param("studentId") int studentId, @Param("courseId") int courseId);

    @Modifying
    @Query("DELETE FROM StudentCourse sc WHERE sc.student.id = :studentId AND sc.course.id = :courseId")
    void deleteStudentCourseByStudentIdAndCourseId(@Param("studentId") int studentId, @Param("courseId") int courseId);

    @Query("SELECT c FROM Course c WHERE c.id NOT IN (SELECT sc.course.id FROM StudentCourse sc WHERE sc.student.id = :studentId)")
    List<Course> findCoursesNotEnrolledByStudentId(@Param("studentId") int studentId);
    @Modifying
    @Query(value = "INSERT INTO student_courses (course_id, student_id, instructor_id) " +
            "VALUES (:courseId, :studentId, :instructorId)", nativeQuery = true)
    void enrollStudentInCourse(@Param("courseId") int courseId, @Param("studentId") int studentId,
                               @Param("instructorId") int instructorId);

    @Query("SELECT s, sc.grade, sc.id FROM Student s " +
            "JOIN StudentCourse sc ON s = sc.student " +
            "JOIN InstructorCourse ic ON sc.course = ic.course " +
            "JOIN Instructor i ON ic.instructor = i " +
            "WHERE i.id = :instructorId AND ic.course.id = :courseId")
    List<Object[]> findStudentsAndMarksWithInstructorCourseIdByInstructorIdAndCourseId(@Param("instructorId") int instructorId,
                                                                                       @Param("courseId") int courseId);
    @Modifying
    @Query("UPDATE StudentCourse sc SET sc.grade = :grade WHERE sc.id = :studentCourseId")
    void updateStudentCourseGradeById(int studentCourseId, @Param("grade") String grade);
    @Modifying
    @Query("DELETE FROM StudentCourse sc WHERE sc.id = :studentCourseId")
    void deleteStudentCourseById(@Param("studentCourseId") int studentCourseId);

    @Query("SELECT c FROM Course c " +
            "WHERE c.department = (SELECT i.department FROM Instructor i WHERE i.id = :instructorId) " +
            "AND c NOT IN (SELECT ic.course FROM InstructorCourse ic WHERE ic.instructor.id = :instructorId)")
    List<Course> getUnassignedCoursesFromSameDept(@Param("instructorId") int instructorId);

    @Modifying
    @Query("DELETE FROM InstructorCourse ic WHERE ic.instructor.id = :instructorId AND ic.course.id = :courseId")
    void removeByInstructorIdAndCourseId(int instructorId, int courseId);

    @Modifying
    @Query(value = "INSERT INTO instructor_courses (instructor_id, course_id) VALUES (:instructorId, :courseId)", nativeQuery = true)
    void assignCourseToInstructor(@Param("courseId") int courseId, @Param("instructorId") int instructorId);
}
