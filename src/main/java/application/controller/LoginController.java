package application.controller;

import application.model.Account;
import application.model.Instructor;
import application.model.Student;
import application.service.AccountService;
import application.service.DepartmentService;
import application.service.InstructorService;
import application.service.StudentService;
import application.util.PasswordHasher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private InstructorService instructorService;
    @Autowired
    private HttpServletRequest request;
    @GetMapping("/login")
    public String viewLoginPage(Model model) {
        return "login_page";
    }
    @PostMapping("/login")
    public String handleAccountLogin(@RequestParam String email, @RequestParam String password) {
        Account account = accountService.findByEmail(email);
        if (!isValidCredentials(account, password)) return "redirect:/login";
        HttpSession session = request.getSession();
        String role = account.getRole();
        if (role.equals("ADMIN")) {
            session.setAttribute("admin", account);
            return "redirect:/admin/dashboard";
        } else if (role.equals("student")) {
            Student student = studentService.findByAccountId(account.getId());
            session.setAttribute("student", student);
            return "redirect:/student/courses";
        } else {
            Instructor instructor = instructorService.findByAccountId(account.getId());
            session.setAttribute("instructor", instructor);
            return "redirect:/instructor/courses";
        }
    }
    private boolean isValidCredentials(Account account, String enteredPassword) {
        if (account == null) return false;
        return PasswordHasher.verifyPassword(account.getHashedPassword(), enteredPassword);
    }
}
