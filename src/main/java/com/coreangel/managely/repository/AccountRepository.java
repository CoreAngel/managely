package com.coreangel.managely.repository;

import com.coreangel.managely.model.Account;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    Optional<Account> findAccountByLogin(String login);

    @Query("select distinct a from Account as a join fetch a.roles Role where a.login = :login order by a.id asc")
    List<Account> findAccountsByLoginWithRole(@Param("login") String login, Pageable pageable);

    default Optional<Account> findAccountByLoginWithRole(String login) {
        return Optional.ofNullable(this.findAccountsByLoginWithRole(login, PageRequest.of(0, 1)).get(0));
    }

}
