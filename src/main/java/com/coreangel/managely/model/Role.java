package com.coreangel.managely.model;

import com.coreangel.managely.model.AccountRole;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated(EnumType.STRING)
    private AccountRole role;

    @ManyToMany(mappedBy = "roles", cascade = { CascadeType.ALL })
    private final Set<Account> accounts = new HashSet<>();
}
