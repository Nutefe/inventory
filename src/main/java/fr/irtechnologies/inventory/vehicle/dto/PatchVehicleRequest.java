package fr.irtechnologies.inventory.vehicle.dto;

import fr.irtechnologies.inventory.vehicle.domain.VehicleStatus;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.UUID;

public record PatchVehicleRequest(
        UUID dealerId,
        String model,
        @PositiveOrZero BigDecimal price,
        VehicleStatus status
) {
}
