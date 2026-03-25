package fr.irtechnologies.inventory.admin.service;

import fr.irtechnologies.inventory.admin.repository.AdminRepository;
import fr.irtechnologies.inventory.dealer.domain.SubscriptionType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumMap;
import java.util.Map;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    /**
     * Documented behavior: overall counts across all tenants.
     */
    @Transactional(readOnly = true)
    public Map<SubscriptionType, Long> countBySubscriptionOverall() {
        Map<SubscriptionType, Long> result = new EnumMap<>(SubscriptionType.class);
        result.put(SubscriptionType.BASIC, 0L);
        result.put(SubscriptionType.PREMIUM, 0L);

        for (Object[] row : adminRepository.countDealersBySubscriptionOverall()) {
            SubscriptionType type = (SubscriptionType) row[0];
            Long cnt = (Long) row[1];
            result.put(type, cnt);
        }
        return result;
    }
}
