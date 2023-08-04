package application.controller;
import application.model.Account;
import application.model.Department;
import application.model.Instructor;
import application.model.Student;
import application.service.AccountService;
import application.service.DepartmentService;
import application.service.InstructorService;
import application.service.StudentService;
import application.util.PasswordHasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
@Controller
public class RegisterController {
    private DepartmentService departmentService;
    private AccountService accountService;
    private StudentService studentService;
    private InstructorService instructorService;
    @Autowired
    public RegisterController(DepartmentService departmentService, AccountService accountService,
                              StudentService studentService, InstructorService instructorService) {
        this.departmentService = departmentService;
        this.accountService = accountService;
        this.studentService = studentService;
        this.instructorService = instructorService;
    }
    @GetMapping("/register")
    public String viewRegistrationPage(Model model) {
        List<Department> departmentList = departmentService.getAllDepartments();
        model.addAttribute("departmentList", departmentList);
        return "register_page";
    }
    @PostMapping("/register")
    public String registerStudent(@RequestParam String email, @RequestParam String password, @RequestParam String role, @RequestParam String studentFirstName,
                                  @RequestParam String studentLastName, @RequestParam String studentAcademicYear, @RequestParam String studentMajor,
                                  @RequestParam String instructorFirstName, @RequestParam String instructorLastName, @RequestParam String instructorDepartment) {
        if (!isEmailValid(email)) {
            return "redirect:/register";
        }
        Account account = registerAccount(email ,password, role);
        if (role.equals("student")) {
            registerStudent(studentFirstName, studentLastName, email, studentMajor, Integer.parseInt(studentAcademicYear), account);
        } else {
            Department department = departmentService.findById( Integer.parseInt(instructorDepartment));
            registerInstructor(instructorFirstName, instructorLastName, email, department, account);
        }
        return "redirect:/login";
    }
    private boolean isEmailValid(String email) {
        if (accountService.findByEmail(email) == null) return true;
        return false;
    }
    private Account registerAccount(String email, String password, String role) {
        String hashedPassword = PasswordHasher.hashPassword(password);
        Account account = new Account();
        account.setEmail(email);
        account.setHashedPassword(hashedPassword);
        account.setRole(role);
        return accountService.createAccount(account);
    }
    private void registerStudent(String firstName, String lastName, String email, String major, int academicYear, Account account) {
        Student student = new Student();
        student.setAccount(account);
        student.setMajor(major);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setAcademicYear(academicYear);
        student.setEmail(email);
        studentService.createStudent(student);
    }
    private void registerInstructor(String firstName, String lastName, String email, Department  department, Account account) {
        Instructor instructor = new Instructor();
        instructor.setDepartment(department);
        instructor.setFirstName(firstName);
        instructor.setLastName(lastName);
        instructor.setEmail(email);
        instructor.setAccount(account);
        instructorService.createInstructor(instructor);
    }
}
