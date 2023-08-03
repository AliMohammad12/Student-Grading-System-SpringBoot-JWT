package application.controller;

import application.model.Course;
import application.model.Department;
import application.model.Instructor;
import application.model.Student;
import application.service.CourseService;
import application.service.DepartmentService;
import application.service.InstructorService;
import application.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private CourseService courseService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private InstructorService instructorService;
    @GetMapping("/admin/dashboard")
    public String viewAdminDashboard() {
        return "admin_dashboard";
    }
    @GetMapping("/admin/courses")
    public String viewCourses(Model model) {
        List<Course> courseList = courseService.getAllCourses();
        List<Department> departmentList = departmentService.getAllDepartments();
        model.addAttribute("courseList", courseList);
        model.addAttribute("departmentList", departmentList);
        return "admin_courses";
    }
    @PostMapping("/admin/courses/{courseId}/delete")
    public String deleteCourse(@PathVariable int courseId) {
        courseService.deleteCourseById(courseId);
        return "redirect:/admin/courses";
    }
    @PostMapping("/admin/courses/add")
    public String addCourse(HttpServletRequest request) {
        String courseName = request.getParameter("courseName");
        String selectedDepartment = request.getParameter("courseDepartment");
        String[] departmentInfo = selectedDepartment.split("-");
        String departmentId = departmentInfo[0];
        String departmentName = departmentInfo[1];

        Department department = new Department();
        department.setDepartmentName(departmentName);
        department.setId(Integer.parseInt(departmentId));

        Course course = new Course();
        course.setDepartment(department);
        course.setCourseName(courseName);

        courseService.createCourse(course);
        return "redirect:/admin/courses";
    }
    @GetMapping("/admin/departments")
    public String viewDepartments(Model model) {
        List<Department> departmentList = departmentService.getAllDepartments();
        request.setAttribute("departmentList", departmentList);
        return "admin_departments";
    }
    @PostMapping("/admin/departments/{departmentId}/delete")
    public String deleteDepartment(@PathVariable("departmentId") int departmentId) {
        departmentService.deleteDepartment(departmentId);
        return "redirect:/admin/departments";
    }
    @PostMapping("/admin/departments/add")
    public String addDepartment(@RequestParam("departmentName") String departmentName) {
        Department department = new Department();
        department.setDepartmentName(departmentName);
        departmentService.createDepartment(department);
        return "redirect:/admin/departments";
    }
    @GetMapping("/admin/students")
    public String viewStudents(Model model) {
        List<Student> studentList = studentService.getAllStudents();
        request.setAttribute("studentList", studentList);
        return "admin_students";
    }
    @PostMapping("/admin/students/{studentId}/delete")
    public String deleteStudent(@PathVariable("studentId") int studentId) {
        studentService.deleteStudent(studentId);
        return "redirect:/admin/students";
    }
    @GetMapping("/admin/instructors")
    public String viewInstructors(Model model) {
        List<Instructor> instructorList = instructorService.getAllInstructors();
        request.setAttribute("instructorList", instructorList);
        return "admin_instructors";
    }
    @PostMapping("/admin/instructors/{instructorId}/delete")
    public String deleteInstructor(@PathVariable("instructorId") int instructorId) {
        instructorService.deleteInstructor(instructorId);
        return "redirect:/admin/instructors";
    }
}
