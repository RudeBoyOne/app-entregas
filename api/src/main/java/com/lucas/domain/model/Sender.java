package com.lucas.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Parameters;
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
@Table(name = "sender")
public class Sender extends PanacheEntityBase {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "char(36)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;
    private String name;
    private Long contract;
    private String cpf;

    public static Optional<Sender> findById(UUID id) {
        return find("from Sender where id = :id", Parameters.with("id", id)).firstResultOptional();
    }

    public static Boolean existsById(UUID id) {
        return find("from Sender where id = :id", Parameters.with("id", id))
                .stream().findFirst().isPresent();
    }

    public static Optional<Sender> findByContract(Long contract) {
        return find("from Sender where contract = :contract", Parameters.with("contract", contract))
                .firstResultOptional();
    }

    public void deleteById(UUID id) {
        delete("from Sender where id = :id", Parameters.with("id", id));
    }
}
