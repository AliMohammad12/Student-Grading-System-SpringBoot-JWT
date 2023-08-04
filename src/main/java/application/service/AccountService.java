package application.service;

import application.model.Account;
import application.repository.AccountRepository;
import application.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }
    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }
    public void deleteById(int id) {
        accountRepository.deleteById(id);
    }
}
