package fr.irtechnologies.inventory.vehicle.dto;

import fr.irtechnologies.inventory.dealer.domain.SubscriptionType;
import fr.irtechnologies.inventory.vehicle.domain.VehicleStatus;

import java.math.BigDecimal;

public record VehicleSearchParams(
        String model,
        VehicleStatus status,
        BigDecimal priceMin,
        BigDecimal priceMax,
        SubscriptionType subscription
) {
}
