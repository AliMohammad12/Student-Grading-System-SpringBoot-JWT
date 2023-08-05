package application.auth;

import application.security.AccountDetails;
import application.security.JwtService;
import application.security.PasswordEncoder;
import application.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    private final AccountService accountService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        AccountDetails accountDetails = AccountDetails.builder()
                .email(request.getEmail())
                .hashedPassword(PasswordEncoder.encodePassword(request.getPassword()))
                .role(request.getRole())
        .build();
        accountService.createAccount(accountDetails);
        String jwtToken = jwtService.generateToken(accountDetails);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
                  request.getEmail(),
                  request.getPassword()
          )
        );
        AccountDetails accountDetails = accountService.findByEmail(request.getEmail());
        if (accountDetails == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        String jwtToken = jwtService.generateToken(accountDetails);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
