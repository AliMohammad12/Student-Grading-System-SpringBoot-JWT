package application.service;

import application.model.Account;
import application.repository.AccountRepository;
import application.security.AccountDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    public AccountDetails createAccount(AccountDetails account) {
        return accountRepository.save(account);
    }
    public AccountDetails findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }
    public void deleteById(int id) {
        accountRepository.deleteById(id);
    }
}
