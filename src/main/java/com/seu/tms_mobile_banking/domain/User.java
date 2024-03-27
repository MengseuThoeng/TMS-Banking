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
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String uuid;

    @Column(length = 50)
    private String name;

    @Column(length = 8)
    private String gender;

    @Column(unique = true)
    private String oneSignalId;

    @Column(unique = true)
    private String studentIdCard;

    private Boolean isDeleted;
    private Boolean isStudent;

    @OneToMany(mappedBy = "user")
    private List<UserAccount> userAccountList;

    @ManyToMany(mappedBy = "user")
    private List<Role> role;
}
