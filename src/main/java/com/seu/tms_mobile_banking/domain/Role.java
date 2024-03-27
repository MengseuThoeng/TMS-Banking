package com.seu.tms_mobile_banking.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 5)
    private String name;

    @ManyToMany
    @JoinTable(name = "users_roles")
    private List<User> user;

    @ManyToMany
    @JoinTable(name = "role_authorities")
    private List<RoleAuthoritie> roleAuthorities;
}
