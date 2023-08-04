package application.controller;
import application.model.*;
import application.service.CourseService;
import application.service.InstructorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import application.service.StudentService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private InstructorService instructorService;
    @Autowired
    private StudentService studentService;
    @GetMapping("/student/courses")
    public String viewStudentCourses(Model model) {
        Student student = getCurrentStudent();
        List<Course> courseList = courseService.findCoursesByStudentID(student.getId());
        model.addAttribute("student", student);
        model.addAttribute("courseList", courseList);
        return "student_courses";
    }
    @GetMapping("/student/profile")
    public String viewStudentProfile(Model model) {
        Student student = getCurrentStudent();
        model.addAttribute("student", student);
        return "student_profile";
    }
    @GetMapping("/student/available_courses")
    public String viewAvailableCourses(Model model) {
        Student student = getCurrentStudent();
        List<Course> availableCourses = courseService.findCoursesNotEnrolledByStudentId(student.getId());
        List<CourseWithInstructorsDTO> coursesInstructors = getCoursesInstructors(availableCourses);
        model.addAttribute("coursesInstructors", coursesInstructors);
        return "student_available_courses";
    }
    private List<CourseWithInstructorsDTO> getCoursesInstructors(List<Course> courseList) {
        List<CourseWithInstructorsDTO> coursesInstructors = new ArrayList<>();
        for (Course course : courseList) {
            int id = course.getId();
            coursesInstructors.add(new CourseWithInstructorsDTO(course, instructorService.findByCourseId(id)));
        }
        return coursesInstructors;
    }
    @PostMapping("/student/courses/{courseId}")
    public String viewCourseDetails(@PathVariable int courseId, Model model) {
        Student student = getCurrentStudent();
        StudentCourse studentCourse = courseService.findStudentCourseByStudentIdAndCourseId(student.getId(), courseId);
        model.addAttribute("studentCourse", studentCourse);
        return "student_course_details";
    }
    @PostMapping("/student/courses/withdraw/{courseId}")
    public String withdrawFromCourse(@PathVariable int courseId, Model model) {
        Student student = getCurrentStudent();
        courseService.deleteStudentCourseByStudentIdAndCourseId(student.getId(), courseId);
        return "redirect:/student/courses";
    }
    @PostMapping("/student/available_courses/enroll/{courseId}")
    public String enrollInCourse(@RequestParam("instructorId") int instructorId, @PathVariable String courseId) {
        Student student = getCurrentStudent();
        courseService.enrollStudentInCourse(Integer.parseInt(courseId), student.getId(), instructorId);
        return "redirect:/student/courses";
    }
    private Student getCurrentStudent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails account = (UserDetails) authentication.getPrincipal();
        String email = account.getUsername();
        return studentService.findByEmail(account.getUsername());
    }
}

