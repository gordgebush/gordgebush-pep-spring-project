package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.example.entity.Account;
import com.example.exception.DuplicateUsernameException;
import com.example.exception.InvalidLoginException;
import com.example.exception.InvalidRegistrationException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account registerAccount(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank()) {
            throw new InvalidRegistrationException("Username cannot be blank.");
        }

        if (account.getPassword() == null || account.getPassword().length() < 4) {
            throw new InvalidRegistrationException("Password must be at least 4 characters.");
        }

        if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
            throw new DuplicateUsernameException("Username already exists.");
        }

        return accountRepository.save(account);
    }

    public Account login(Account account){
        if(account.getUsername() == null || account.getPassword() == null){
            throw new InvalidLoginException("User and password required");
        }

        Optional<Account> accOptional = accountRepository.findByUsername(account.getUsername());

        if(accOptional.isPresent()){
            Account x = accOptional.get();
            if(x.getPassword().equals(account.getPassword()))
            return x;
            else
            throw new InvalidLoginException("Invalid pass");
        }
        throw new InvalidLoginException("Invalid user");
    }
}
