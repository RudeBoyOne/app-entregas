package com.lucas.domain.model;

import com.lucas.domain.model.enums.StatusDelivery;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Parameters;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "delivery")
public class Delivery extends PanacheEntityBase {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "char(36)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    private BigDecimal tax;
    private LocalDateTime shippingDate;
    private LocalDateTime deliveryDate;
    private LocalDateTime cancellationDate;

    @Enumerated(EnumType.STRING)
    private StatusDelivery status;

    @OneToOne
    private Sender sender;

    @OneToOne
    private Addressee addressee;

    @OneToOne
    private Item item;

    public static Optional<Delivery> findByItem(Item item) {
        return find("from Delivery where item = :item", Parameters.with("item", item)).firstResultOptional();
    }

    public static List<Delivery> findBySender(Sender sender) {
        return list("from Delivery where sender = :sender", Parameters.with("sender", sender));
    }

    public static List<Delivery> findByAddressee(Addressee addressee) {
        return list("from Delivery where addressee = :addressee", Parameters.with("addressee", addressee));
    }
}
