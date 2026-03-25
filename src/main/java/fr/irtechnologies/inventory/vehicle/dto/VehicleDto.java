package fr.irtechnologies.inventory.vehicle.dto;

import fr.irtechnologies.inventory.dealer.domain.Dealer;
import fr.irtechnologies.inventory.vehicle.domain.VehicleStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class VehicleDto {
    private UUID id;
    private String tenantId;
    private Dealer dealer;
    private String model;
    private BigDecimal price;
    private VehicleStatus status;
}
