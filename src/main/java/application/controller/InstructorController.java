package application.controller;

import application.model.Course;
import application.model.Instructor;
import application.model.Student;
import application.service.CourseService;
import application.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class InstructorController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private InstructorService instructorService;
    @GetMapping("/instructor/courses")
    public String viewInstructorCourses(Model model) {
        Instructor instructor = getCurrentInstructor();
        List<Course> courseList = courseService.findCoursesByInstructorId(instructor.getId());
        model.addAttribute("instructor", instructor);
        model.addAttribute("courseList", courseList);
        return "instructor_courses";
    }
    @GetMapping("/instructor/courses/{courseId}")
    public String viewCourseDetails(@PathVariable int courseId, Model model) {
        Instructor instructor = getCurrentInstructor();
        List<Object[]> courseDetails = courseService.findStudentsAndMarksWithInstructorCourseIdByInstructorIdAndCourseId(instructor.getId(), courseId);
        model.addAttribute("courseDetails", courseDetails);
        model.addAttribute("course_Id", courseId);
        return "instructor_course_details";
    }
    @PostMapping("/instructor/course/{courseId}/edit_grade")
    public String updateStudentGrade(@PathVariable int courseId, @RequestParam("studentCourseId") int studentCourseId,
                                            @RequestParam("grades") String grade) {
        courseService.updateStudentCourseGradeById(studentCourseId, grade);
        return "redirect:/instructor/courses/" + courseId;
    }
    @PostMapping("/instructor/course/{courseId}/remove")
    public String withdrawCourse(@PathVariable("courseId") int courseId, @RequestParam("instructorId") int instructorId) {
        courseService.removeInstructorCourseByInstructorIdAndCourseId(instructorId, courseId);
       // courseService.removeStudentCourseByInstructorIdAndCourseId(instructorId, courseId);
        return "redirect:/instructor/courses";
    }
    @PostMapping("/instructor/course/{courseId}/{studentId}/remove")
    public String removeStudentCourse(@PathVariable("courseId") int courseId, @PathVariable("studentId") int studentId,
                                      @RequestParam("studentCourseId") int studentCourseId) {
        courseService.deleteStudentCourseById(studentCourseId);
        return "redirect:/instructor/courses";
    }
    @GetMapping("/instructor/available_courses")
    public String viewAvailableCoursesToTeach(Model model) {
        Instructor instructor = getCurrentInstructor();
        List<Course> availableCourses = courseService.getUnassignedCoursesFromSameDept(instructor.getId());
        model.addAttribute("availableCourses", availableCourses);
        return "instructor_available_courses";
    }
    @PostMapping("/instructor/available_courses/{courseId}/teach")
    public String teachCourse(@PathVariable("courseId") int courseId) {
        Instructor instructor = getCurrentInstructor();
        courseService.assignCourseToInstructor(courseId, instructor.getId());
        return "redirect:/instructor/courses";
    }
    @GetMapping("/instructor/profile")
    public String viewProfile(Model model){
        Instructor instructor = getCurrentInstructor();
        model.addAttribute("instructor", instructor);
        return "instructor_profile";
    }
    private Instructor getCurrentInstructor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails account = (UserDetails) authentication.getPrincipal();
        return instructorService.findByEmail(account.getUsername());
    }
}
