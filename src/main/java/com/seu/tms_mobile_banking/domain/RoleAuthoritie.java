package com.seu.tms_mobile_banking.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name="role_authorities")
public class RoleAuthoritie {
    @Id
    private Integer id;

    @ManyToMany(mappedBy = "roleAuthorities")
    private List<Role> role;

}
