package fr.irtechnologies.inventory.dealer.dto;

import fr.irtechnologies.inventory.dealer.domain.SubscriptionType;
import jakarta.validation.constraints.Email;

public record PatchDealerRequest(
        String name,
        @Email String email,
        SubscriptionType subscriptionType
) {
}
