package fr.irtechnologies.inventory.dealer.dto;

import fr.irtechnologies.inventory.dealer.domain.SubscriptionType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateDealerRequest(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotNull SubscriptionType subscriptionType
) {
}
