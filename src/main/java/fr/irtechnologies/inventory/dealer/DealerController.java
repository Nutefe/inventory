package fr.irtechnologies.inventory.dealer;

import fr.irtechnologies.inventory.dealer.dto.CreateDealerRequest;
import fr.irtechnologies.inventory.dealer.dto.DealerDto;
import fr.irtechnologies.inventory.dealer.dto.PatchDealerRequest;
import fr.irtechnologies.inventory.dealer.service.DealerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/dealers")
public class DealerController {
    private final DealerService service;

    public DealerController(DealerService service) {
        this.service = service;
    }

    @PostMapping
    public DealerDto create(@Valid @RequestBody CreateDealerRequest req) {
        return service.create(req.name(), req.email(), req.subscriptionType());
    }

    @GetMapping("/{id}")
    public DealerDto get(@PathVariable UUID id) {
        return service.get(id);
    }

    @GetMapping
    public Page<DealerDto> list(Pageable pageable) {
        return service.list(pageable);
    }

    @PatchMapping("/{id}")
    public DealerDto patch(@PathVariable UUID id, @Valid @RequestBody PatchDealerRequest req) {
        return service.patch(id, req.name(), req.email(), req.subscriptionType());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
