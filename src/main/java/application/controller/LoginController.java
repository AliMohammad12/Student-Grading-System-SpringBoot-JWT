package application.controller;

import application.model.Account;
import application.model.Instructor;
import application.model.Student;
import application.service.AccountService;
import application.service.DepartmentService;
import application.service.InstructorService;
import application.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private InstructorService instructorService;
    @GetMapping("/login")
    public String viewLoginPage(Model model) {
        return "login_page";
    }
}
