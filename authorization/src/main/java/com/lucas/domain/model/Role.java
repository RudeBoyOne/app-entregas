package com.lucas.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Parameters;
import io.quarkus.security.jpa.RolesValue;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "role")
public class Role extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @RolesValue
    private String role;

    public static Role findById(Long id) {
        return find("from Role where id = :id", Parameters.with("id", id)).firstResult();
    }
}
