package com.coreangel.managely.service;

import com.coreangel.managely.model.Account;
import com.coreangel.managely.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository repository;

    @Autowired
    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Optional<Account> createAccount(Account account) {
        if (!this.isAccountExistWithLogin(account)) {
            account.setPassword(BCrypt.hashpw(account.getPassword(), BCrypt.gensalt()));
            return Optional.of(this.repository.save(account));
        }

        return Optional.empty();
    }

    @Transactional
    public boolean updateAccount(Account account) {
        Optional<Account> optAccount = this.findAccountById(account.getId());

        if (optAccount.isPresent()) {
            this.repository.save(account);
            return true;
        }

        return false;
    }

    @Transactional
    public void deleteAccount(long id) {
        this.repository.deleteById(id);
    }

    @Transactional
    public boolean isAccountExistWithLogin(Account account) {
        return this.repository.findAccountByLogin(account.getLogin()).isPresent();
    }

    @Transactional
    public Optional<Account> findAccountById(long id) {
        return this.repository.findById(id);
    }

}
