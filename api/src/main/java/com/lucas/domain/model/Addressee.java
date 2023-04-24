package com.lucas.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Optional;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "addressee")
public class Addressee extends PanacheEntityBase {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "char(36)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    private String name;
    private String city;
    private String state;
    private String cep;
    private Long houseNumber;
    private String neighborhood;
    private String street;
    private String longitude;
    private String latitude;

    public static Optional<Addressee> findByCepAndHouseNumber(String cep, Long houseNumber) {
        return find("select a from Addressee a where a.cep = ?1 and a.houseNumber = ?2", cep, houseNumber)
                .firstResultOptional();
    }
}
