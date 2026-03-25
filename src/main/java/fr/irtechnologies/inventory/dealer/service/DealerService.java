package fr.irtechnologies.inventory.dealer.service;

import fr.irtechnologies.inventory.config.TenantContext;
import fr.irtechnologies.inventory.dealer.repository.DealerRepository;
import fr.irtechnologies.inventory.dealer.domain.Dealer;
import fr.irtechnologies.inventory.dealer.domain.SubscriptionType;
import fr.irtechnologies.inventory.dealer.dto.DealerDto;
import fr.irtechnologies.inventory.dealer.mapper.DealerMapper;
import fr.irtechnologies.inventory.exception.BadRequestException;
import fr.irtechnologies.inventory.exception.ForbiddenException;
import fr.irtechnologies.inventory.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DealerService {
    private final DealerRepository dealerRepository;

    public DealerService(DealerRepository dealerRepository) {
        this.dealerRepository = dealerRepository;
    }

    @Transactional
    public DealerDto create(String name, String email, SubscriptionType subscriptionType) {
        String tenantId = requireTenant();
        Dealer saved = dealerRepository.save(new Dealer(null, tenantId, name, email, subscriptionType));
        return DealerMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public DealerDto get(UUID id) {
        String tenantId = requireTenant();
        return dealerRepository.findByIdAndTenantId(id, tenantId)
                .map(DealerMapper::toDto)
                .orElseThrow(() -> new ForbiddenException("Dealer not found"));
    }

    @Transactional(readOnly = true)
    public Page<DealerDto> list(Pageable pageable) {
        String tenantId = requireTenant();
        return dealerRepository.findAllByTenantId(tenantId, pageable).map(DealerMapper::toDto);
    }

    @Transactional
    public DealerDto patch(UUID id, String name, String email, SubscriptionType subscriptionType) {
        String tenantId = requireTenant();
        Dealer e = dealerRepository.findById(id).orElseThrow(() -> new NotFoundException("Dealer not found"));
        if (!tenantId.equals(e.getTenantId())) {
            throw new ForbiddenException("Cross-tenant access blocked");
        }
        if (name != null) e.setName(name);
        if (email != null) e.setEmail(email);
        if (subscriptionType != null) e.setSubscriptionType(subscriptionType);
        return DealerMapper.toDto(dealerRepository.save(e));
    }

    @Transactional
    public void delete(UUID id) {
        String tenantId = requireTenant();
        Dealer e = dealerRepository.findById(id).orElseThrow(() -> new NotFoundException("Dealer not found"));
        if (!tenantId.equals(e.getTenantId())) {
            throw new ForbiddenException("Cross-tenant access blocked");
        }
        dealerRepository.delete(e);
    }

    private String requireTenant() {
        String t = TenantContext.getTenantId();
        if (t == null || t.isBlank()) {
            throw new BadRequestException("Missing X-Tenant-Id header");
        }
        return t;
    }
}
