package com.katcote.propertymanagement.controller;

import com.katcote.propertymanagement.entity.Lease;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/leases")
public class LeaseController {

    private List<Lease> leases = new ArrayList<>();

    @PostMapping
    public Lease createLease() {
        Lease lease = new Lease();
        leases.add(lease);
        return lease;
    }

    @GetMapping
    public List<Lease> getAllLeases() {
        return leases;
    }

    @GetMapping("/{id}")
    public Lease getLease(@PathVariable Long id) {
        return leases.stream()
                .filter(l -> l.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PutMapping("/{id}")
    public Lease updateLease(@PathVariable Long id) {
        Lease lease = getLease(id);
        if (lease != null) {
            // Логика обновления будет добавлена позже
            return lease;
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteLease(@PathVariable Long id) {
        leases.removeIf(l -> l.getId().equals(id));
    }
}
