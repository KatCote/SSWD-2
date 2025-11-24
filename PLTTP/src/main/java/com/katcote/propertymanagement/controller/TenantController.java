package com.katcote.propertymanagement.controller;

import com.katcote.propertymanagement.entity.Tenant;
import com.katcote.propertymanagement.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    @Autowired
    private TenantRepository tenantRepository;

    @PostMapping
    public Tenant createTenant(@RequestBody Tenant tenant) {
        if (tenant == null) { tenant = new Tenant(); }
        return tenantRepository.save(tenant);
    }

    @GetMapping
    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tenant> getTenant(@PathVariable Long id) {
        Optional<Tenant> tenant = tenantRepository.findById(id);
        return tenant.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tenant> updateTenant(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<Tenant> optionalTenant = tenantRepository.findById(id);
        if (optionalTenant.isPresent()) {
            Tenant existingTenant = optionalTenant.get();

            for (Map.Entry<String, Object> entry : updates.entrySet()) {
                String fieldName = entry.getKey();
                Object value = entry.getValue();

                switch (fieldName) {
                    case "firstName":
                        existingTenant.setFirstName((String) value);
                        break;
                    case "lastName":
                        existingTenant.setLastName((String) value);
                        break;
                    case "email":
                        existingTenant.setEmail((String) value);
                        break;
                    case "phone":
                        existingTenant.setPhone((String) value);
                        break;
                    case "dateOfBirth":
                        if (value instanceof String)
                        { existingTenant.setDateOfBirth(LocalDate.parse((String) value)); }
                        break;
                    case "taxId":
                        existingTenant.setTaxId((String) value);
                        break;
                    case "emergencyContact":
                        existingTenant.setEmergencyContact((String) value);
                        break;
                    case "employmentStatus":
                        existingTenant.setEmploymentStatus((String) value);
                        break;
                    case "monthlyIncome":
                        existingTenant.setMonthlyIncome((Integer) value);
                        break;
                    case "creditScore":
                        existingTenant.setCreditScore((Integer) value);
                        break;
                    case "notes":
                        existingTenant.setNotes((String) value);
                        break;
                    case "active":
                        existingTenant.setActive((Boolean) value);
                        break;
                    default:
                        System.out.println("⚠️  Unknown field: " + fieldName);
                }
            }

            Tenant saved = tenantRepository.save(existingTenant);
            return ResponseEntity.ok(saved);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTenant(@PathVariable Long id) {
        if (tenantRepository.existsById(id)) {
            tenantRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}