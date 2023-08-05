package application.security;

import application.model.Account;
import application.repository.AccountRepository;
import application.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountDetails accountDetails = accountRepository.findByEmail(username);
        if (accountDetails == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        return accountDetails;
    }
}
