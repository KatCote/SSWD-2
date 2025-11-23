package com.katcote.propertymanagement.controller;

import com.katcote.propertymanagement.entity.Tenant;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    private List<Tenant> tenants = new ArrayList<>();

    @PostMapping
    public Tenant createTenant() {
        Tenant tenant = new Tenant();
        tenants.add(tenant);
        return tenant;
    }

    @GetMapping
    public List<Tenant> getAllTenants() {
        return tenants;
    }

    @GetMapping("/{id}")
    public Tenant getTenant(@PathVariable Long id) {
        return tenants.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PutMapping("/{id}")
    public Tenant updateTenant(@PathVariable Long id) {
        Tenant tenant = getTenant(id);
        if (tenant != null) {
            // Логика обновления будет добавлена позже
            return tenant;
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteTenant(@PathVariable Long id) {
        tenants.removeIf(t -> t.getId().equals(id));
    }
}
