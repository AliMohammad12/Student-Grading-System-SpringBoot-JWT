package application.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        if (role.equals("ADMIN")) {
            response.sendRedirect("/admin/dashboard");
        } else if (role.equals("instructor")) {
            response.sendRedirect("/instructor/courses");
        } else {
            response.sendRedirect("/student/courses");
        }
    }
}