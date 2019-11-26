package com.coreangel.managely.account;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.Set;

@Builder
@Getter
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String login;
    private String password;
    @ManyToMany
    @JoinTable(
            name = "account_role",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public void addRole(AccountRole role) {
        if (role != null) {
            Role roleEntity = new Role();
            roleEntity.setRole(role);
            roles.add(roleEntity);
        }
    }
}
