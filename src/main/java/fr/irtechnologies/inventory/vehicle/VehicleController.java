package fr.irtechnologies.inventory.vehicle;

import fr.irtechnologies.inventory.dealer.domain.SubscriptionType;
import fr.irtechnologies.inventory.vehicle.domain.VehicleStatus;
import fr.irtechnologies.inventory.vehicle.dto.CreateVehicleRequest;
import fr.irtechnologies.inventory.vehicle.dto.PatchVehicleRequest;
import fr.irtechnologies.inventory.vehicle.dto.VehicleDto;
import fr.irtechnologies.inventory.vehicle.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {
    private final VehicleService service;

    public VehicleController(VehicleService service) {
        this.service = service;
    }

    @PostMapping
    public VehicleDto create(@Valid @RequestBody CreateVehicleRequest req) {
        System.out.println("Received create vehicle request: " + req);
        return service.create(req.dealerId(), req.model(), req.price(), req.status());
    }

    @GetMapping("/{id}")
    public VehicleDto get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping
    public Page<VehicleDto> search(
            @RequestParam(required = false) String model,
            @RequestParam(required = false) VehicleStatus status,
            @RequestParam(required = false) BigDecimal priceMin,
            @RequestParam(required = false) BigDecimal priceMax,
            @RequestParam(required = false) SubscriptionType subscription,
            Pageable pageable
    ) {
        return service.search(model, status, priceMin, priceMax, subscription, pageable);
    }

    @PatchMapping("/{id}")
    public VehicleDto patch(@PathVariable UUID id, @Valid @RequestBody PatchVehicleRequest req) {
        return service.patch(id, req.dealerId(), req.model(), req.price(), req.status());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
