package com.lucas.domain.model;

import com.lucas.domain.model.enums.TypeItem;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Parameters;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "item")
public class Item extends PanacheEntityBase {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "char(36)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    private String code;

    @Enumerated(EnumType.STRING)
    private TypeItem typeItem;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Sender owner;

    public static Optional<Item> findByCode(String code) {
        return find("from Item where code = :code", Parameters.with("code", code))
                .firstResultOptional();
    }

    public static List<Item> listByOwner(Sender owner) {
        return list("select i from Item i where i.owner = ?1", owner);
    }
}
