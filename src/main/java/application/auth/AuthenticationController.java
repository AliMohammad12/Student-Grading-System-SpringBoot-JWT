package application.auth;

import application.model.Department;
import application.service.DepartmentService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @Autowired
    private DepartmentService departmentService;
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginRequest", new AuthenticationRequest());
        return "login_page";
    }
    @GetMapping("/register")
    public String register(Model model) {
        List<Department> departmentList = departmentService.getAllDepartments();
        model.addAttribute("departmentList", departmentList);
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register_page";
    }
    @PostMapping("/api/v1/auth/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping(path = "/api/v1/auth/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        System.out.println(authenticationService.authenticate(request).getToken());
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
