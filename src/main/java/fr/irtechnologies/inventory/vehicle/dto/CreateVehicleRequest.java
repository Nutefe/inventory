package fr.irtechnologies.inventory.vehicle.dto;

import fr.irtechnologies.inventory.vehicle.domain.VehicleStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateVehicleRequest(
        @NotNull UUID dealerId,
        @NotBlank String model,
        @NotNull @PositiveOrZero BigDecimal price,
        @NotNull VehicleStatus status
) {
}
