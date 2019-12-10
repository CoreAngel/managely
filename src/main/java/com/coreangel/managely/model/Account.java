package com.coreangel.managely.model;

import lombok.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String login;
    private String password;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "account_role",
            joinColumns = { @JoinColumn(name = "accounts_id") },
            inverseJoinColumns = { @JoinColumn(name = "roles_id") }
    )
    private final Set<Role> roles = new HashSet<>();

    public void addRole(AccountRole role) {
        if (role != null) {
            Role roleEntity = new Role();
            roleEntity.setRole(role);
            roles.add(roleEntity);
        }
    }

    public boolean isPasswordMatch(String password) {
        return BCrypt.checkpw(password, this.password);
    }
}
