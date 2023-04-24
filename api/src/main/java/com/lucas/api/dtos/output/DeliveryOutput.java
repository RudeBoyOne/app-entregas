package com.lucas.api.dtos.output;

import com.lucas.domain.model.enums.StatusDelivery;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class DeliveryOutput {

    private UUID id;
    private BigDecimal tax;
    private LocalDateTime shippingDate;
    private LocalDateTime deliveryDate;
    private LocalDateTime cancellationDate;
    private StatusDelivery status;
    private SenderOutput sender;
    private AddresseeOutput addressee;
    private ItemOutput item;

}
