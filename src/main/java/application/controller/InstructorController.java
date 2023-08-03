package application.controller;

import application.model.Course;
import application.model.Instructor;
import application.model.Student;
import application.service.CourseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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
    private HttpServletRequest request;
    @GetMapping("/instructor/courses")
    public String viewInstructorCourses(Model model) {
        Instructor instructor = getInstructorFromSession();
        List<Course> courseList = courseService.findCoursesByInstructorId(instructor.getId());
        model.addAttribute("instructor", instructor);
        model.addAttribute("courseList", courseList);
        return "instructor_courses";
    }
    @GetMapping("/instructor/courses/{courseId}")
    public String viewCourseDetails(@PathVariable int courseId, Model model) {
        Instructor instructor = getInstructorFromSession();
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
    public String removeStudentFromCourse(@PathVariable int courseId, @RequestParam("studentCourseId") int studentCourseId) {
        courseService.deleteStudentCourseById(studentCourseId);
        return "redirect:/instructor/courses/" + courseId;
    }

    private Instructor getInstructorFromSession() {
        HttpSession session = request.getSession();
        return (Instructor) session.getAttribute("instructor");
    }
}
