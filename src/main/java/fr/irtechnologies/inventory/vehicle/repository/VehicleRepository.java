package fr.irtechnologies.inventory.vehicle.repository;

import fr.irtechnologies.inventory.dealer.domain.SubscriptionType;
import fr.irtechnologies.inventory.vehicle.domain.Vehicle;
import fr.irtechnologies.inventory.vehicle.domain.VehicleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
    @Query("""
    select v from Vehicle v
    join fetch v.dealer d
    where v.id = :id and v.tenantId = :tenantId
  """)
    Optional<Vehicle> findByIdAndTenantIdFetchDealer(@Param("id") UUID id, @Param("tenantId") String tenantId);

    @Query("""
    select v from Vehicle v
    join fetch v.dealer d
    where v.tenantId = :tenantId
      and (:model is null or lower(v.model) like concat('%', lower(:model), '%'))
      and (:status is null or v.status = :status)
      and (:priceMin is null or v.price >= :priceMin)
      and (:priceMax is null or v.price <= :priceMax)
      and (:subscription is null or d.subscriptionType = :subscription)
  """)
    Page<Vehicle> searchTenantScoped(
            @Param("tenantId") String tenantId,
            @Param("model") String model,
            @Param("status") VehicleStatus status,
            @Param("priceMin") BigDecimal priceMin,
            @Param("priceMax") BigDecimal priceMax,
            @Param("subscription") SubscriptionType subscription,
            Pageable pageable
    );
}
