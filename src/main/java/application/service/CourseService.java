package application.service;

import application.model.Course;
import application.model.Instructor;
import application.model.Student;
import application.model.StudentCourse;
import application.repository.CourseRepository;
import application.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public List<Course> findCoursesByStudentID(int studentId){
        return courseRepository.findCoursesByStudentID(studentId);
    }
    public StudentCourse findStudentCourseByStudentIdAndCourseId(int studentId, int courseId) {
        return courseRepository.findStudentCourseByStudentIdAndCourseId(studentId, courseId);
    }
    @Transactional
    public void deleteStudentCourseByStudentIdAndCourseId(int studentId, int courseId) {
        courseRepository.deleteStudentCourseByStudentIdAndCourseId(studentId, courseId);
    }
    public List<Course> findCoursesNotEnrolledByStudentId(int studentId) {
        return courseRepository.findCoursesNotEnrolledByStudentId(studentId);
    }
    @Transactional
    public void enrollStudentInCourse(int courseId, int studentId, int instructorId) {
        courseRepository.enrollStudentInCourse(courseId, studentId, instructorId);
    }
    public List<Course>findCoursesByInstructorId (int instructorId) {
        return courseRepository.findCoursesByInstructorId(instructorId);
    }
    public List<Object[]> findStudentsAndMarksWithInstructorCourseIdByInstructorIdAndCourseId(int instructorId, int studentId) {
        return courseRepository.findStudentsAndMarksWithInstructorCourseIdByInstructorIdAndCourseId(instructorId, studentId);
    }
    @Transactional
    public void updateStudentCourseGradeById(int studentCourseId, String grade) {
        courseRepository.updateStudentCourseGradeById(studentCourseId, grade);
    }
    @Transactional
    public  void deleteStudentCourseById(int studentCourseId) {
        courseRepository.deleteStudentCourseById(studentCourseId);
    }
    public List<Course> getUnassignedCoursesFromSameDept(int instructorId) {
        return courseRepository.getUnassignedCoursesFromSameDept(instructorId);
    }
    @Transactional
    public void removeInstructorCourseByInstructorIdAndCourseId(int instructorId, int courseId) {
        courseRepository.removeInstructorCourseByInstructorIdAndCourseId(instructorId, courseId);
    }
    @Transactional
    public void assignCourseToInstructor(int courseId, int instructorId) {
        courseRepository.assignCourseToInstructor(courseId, instructorId);
    }
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    public void deleteCourseById(int courseId) {
        courseRepository.deleteById(courseId);
    }
    public void createCourse(Course course) {
        courseRepository.save(course);
    }
}
