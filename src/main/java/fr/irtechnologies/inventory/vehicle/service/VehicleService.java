package fr.irtechnologies.inventory.vehicle.service;

import fr.irtechnologies.inventory.config.TenantContext;
import fr.irtechnologies.inventory.dealer.domain.Dealer;
import fr.irtechnologies.inventory.dealer.repository.DealerRepository;
import fr.irtechnologies.inventory.dealer.domain.SubscriptionType;
import fr.irtechnologies.inventory.exception.BadRequestException;
import fr.irtechnologies.inventory.exception.ForbiddenException;
import fr.irtechnologies.inventory.exception.NotFoundException;
import fr.irtechnologies.inventory.vehicle.repository.VehicleRepository;
import fr.irtechnologies.inventory.vehicle.domain.Vehicle;
import fr.irtechnologies.inventory.vehicle.domain.VehicleStatus;
import fr.irtechnologies.inventory.vehicle.dto.VehicleDto;
import fr.irtechnologies.inventory.vehicle.mapper.VehicleMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final DealerRepository dealerRepository;

    public VehicleService(VehicleRepository vehicleRepository,
                          DealerRepository dealerRepository) {
        this.vehicleRepository = vehicleRepository;
        this.dealerRepository = dealerRepository;
    }

    @Transactional
    public VehicleDto create(UUID dealerId, String model, BigDecimal price, VehicleStatus status) {
        String tenantId = requireTenant();
        Dealer dealer = dealerRepository.findById(dealerId)
                .orElseThrow(() -> new NotFoundException("Dealer not found"));
        if (!tenantId.equals(dealer.getTenantId())) {
            throw new ForbiddenException("Cross-tenant access blocked");
        }

        Vehicle v = new Vehicle();
        v.setTenantId(tenantId);
        v.setDealer(dealer);
        v.setModel(model);
        v.setPrice(price);
        v.setStatus(status);

        return VehicleMapper.toDto(vehicleRepository.save(v));
    }

    @Transactional(readOnly = true)
    public VehicleDto get(UUID id) {
        String tenantId = requireTenant();
        Vehicle v = vehicleRepository.findByIdAndTenantIdFetchDealer(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Vehicle not found"));
        return VehicleMapper.toDto(v);
    }

    @Transactional(readOnly = true)
    public Page<VehicleDto> search(String model,
                                VehicleStatus status,
                                BigDecimal priceMin,
                                BigDecimal priceMax,
                                SubscriptionType subscription,
                                Pageable pageable) {
        String tenantId = requireTenant();
        return vehicleRepository.searchTenantScoped(tenantId, model, status, priceMin, priceMax, subscription, pageable)
                .map(VehicleMapper::toDto);
    }

    @Transactional
    public VehicleDto patch(UUID id, UUID dealerId, String model, BigDecimal price, VehicleStatus status) {
        String tenantId = requireTenant();

        Vehicle v = vehicleRepository.findById(id).orElseThrow(() -> new NotFoundException("Vehicle not found"));
        if (!tenantId.equals(v.getTenantId())) {
            throw new ForbiddenException("Cross-tenant access blocked");
        }

        if (dealerId != null) {
            Dealer dealer = dealerRepository.findById(dealerId)
                    .orElseThrow(() -> new NotFoundException("Dealer not found"));
            if (!tenantId.equals(dealer.getTenantId())) {
                throw new ForbiddenException("Cross-tenant access blocked");
            }
            v.setDealer(dealer);
        }
        if (model != null) v.setModel(model);
        if (price != null) v.setPrice(price);
        if (status != null) v.setStatus(status);

        return VehicleMapper.toDto(vehicleRepository.save(v));
    }

    @Transactional
    public void delete(UUID id) {
        String tenantId = requireTenant();

        Vehicle v = vehicleRepository.findById(id).orElseThrow(() -> new NotFoundException("Vehicle not found"));
        if (!tenantId.equals(v.getTenantId())) {
            throw new ForbiddenException("Cross-tenant access blocked");
        }
        vehicleRepository.delete(v);
    }

    private String requireTenant() {
        String t = TenantContext.getTenantId();
        if (t == null || t.isBlank()) {
            throw new BadRequestException("Missing X-Tenant-Id header");
        }
        return t;
    }
}
