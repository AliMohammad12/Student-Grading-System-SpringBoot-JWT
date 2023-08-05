package application.repository;

import application.model.Account;
import application.security.AccountDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountDetails, Integer> {
    AccountDetails findByEmail(String email);
}
