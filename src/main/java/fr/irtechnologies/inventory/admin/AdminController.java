package fr.irtechnologies.inventory.admin;

import fr.irtechnologies.inventory.admin.dto.CountBySubscriptionResponse;
import fr.irtechnologies.inventory.admin.service.AdminService;
import fr.irtechnologies.inventory.dealer.domain.SubscriptionType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService service;

    public AdminController(AdminService service) {
        this.service = service;
    }

    /**
     * Documented behavior:
     * - This endpoint returns OVERALL counts across ALL tenants.
     * - Requires GLOBAL_ADMIN role.
     */
    @GetMapping("/dealers/countBySubscription")
    @PreAuthorize("hasRole('GLOBAL_ADMIN')")
    public CountBySubscriptionResponse countBySubscription() {
        Map<SubscriptionType, Long> m = service.countBySubscriptionOverall();
        return new CountBySubscriptionResponse(m.get(SubscriptionType.BASIC), m.get(SubscriptionType.PREMIUM));
    }
}
