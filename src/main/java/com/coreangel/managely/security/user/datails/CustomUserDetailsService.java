package com.coreangel.managely.security.user.datails;

import com.coreangel.managely.model.Account;
import com.coreangel.managely.service.AccountService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountService accountService;

    public CustomUserDetailsService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Account> optionalAccount = this.accountService.findAccountByLoginWithRolesLoaded(login);

        if (optionalAccount.isEmpty()) {
            throw new UsernameNotFoundException(login);
        }

        return new CustomUserDetails(optionalAccount.get());
    }
}
