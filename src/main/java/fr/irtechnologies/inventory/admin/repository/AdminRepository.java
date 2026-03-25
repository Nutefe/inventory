package fr.irtechnologies.inventory.admin.repository;

import fr.irtechnologies.inventory.dealer.domain.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<Dealer, UUID> {
    @Query("""
    select d.subscriptionType as subscriptionType, count(d) as cnt
    from Dealer d
    group by d.subscriptionType
  """)
    List<Object[]> countDealersBySubscriptionOverall();
}
