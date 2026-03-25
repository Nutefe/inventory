package fr.irtechnologies.inventory.dealer.repository;

import fr.irtechnologies.inventory.dealer.domain.Dealer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DealerRepository extends JpaRepository<Dealer, UUID> {
    Optional<Dealer> findByIdAndTenantId(UUID id, String tenantId);
    Page<Dealer> findAllByTenantId(String tenantId, Pageable pageable);
}
