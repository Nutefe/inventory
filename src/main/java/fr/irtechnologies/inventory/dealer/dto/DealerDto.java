package fr.irtechnologies.inventory.dealer.dto;

import fr.irtechnologies.inventory.dealer.domain.SubscriptionType;
import lombok.Data;

import java.util.UUID;

@Data
public class DealerDto {
    private UUID id;
    private String tenantId;
    private String name;
    private String email;
    private SubscriptionType subscriptionType;
}
