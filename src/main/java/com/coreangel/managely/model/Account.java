package com.coreangel.managely.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Builder
@Getter
@Setter
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String login;
    private String password;

    @OneToMany
    private Set<Role> roles;

    public void addRole(AccountRole role) {
        if (role != null) {
            Role roleEntity = new Role();
            roleEntity.setRole(role);
            roles.add(roleEntity);
        }
    }
}
