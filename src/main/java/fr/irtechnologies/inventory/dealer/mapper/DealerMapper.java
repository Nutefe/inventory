package fr.irtechnologies.inventory.dealer.mapper;

import fr.irtechnologies.inventory.dealer.domain.Dealer;
import fr.irtechnologies.inventory.dealer.dto.DealerDto;

public class DealerMapper {
    public static DealerDto toDto(Dealer dealer) {
        DealerDto dto = new DealerDto();
        dto.setId(dealer.getId());
        dto.setTenantId(dealer.getTenantId());
        dto.setName(dealer.getName());
        dto.setEmail(dealer.getEmail());
        dto.setSubscriptionType(dealer.getSubscriptionType());
        return dto;
    }

    public static Dealer toDealer(DealerDto dto) {
        Dealer dealer = new Dealer();
        dealer.setId(dto.getId());
        dealer.setTenantId(dto.getTenantId());
        dealer.setName(dto.getName());
        dealer.setEmail(dto.getEmail());
        dealer.setSubscriptionType(dto.getSubscriptionType());
        return dealer;
    }
}
