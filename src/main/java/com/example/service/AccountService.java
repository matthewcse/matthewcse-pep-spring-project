package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account register(Account account){
        Optional<Account> a = accountRepository.findByUsername(account.getUsername());
        if (a.isPresent()) return new Account();//blank for duplicate username
        if (account.getUsername().length() > 0 && account.getPassword().length() >= 4){
            return accountRepository.save(account);
        }
        else return null;//null for any other error
    }

    public Account login(Account account){
        Optional<Account> a = accountRepository.findByUsername(account.getUsername());
        if (a.isPresent()){ 
            if (account.getPassword().equals(a.get().getPassword())){
                return a.get();
            }
        }
        return null;//username not found
    }
}
