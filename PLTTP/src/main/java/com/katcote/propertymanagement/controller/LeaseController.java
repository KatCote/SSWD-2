package com.katcote.propertymanagement.controller;

import com.katcote.propertymanagement.entity.Landlord;
import com.katcote.propertymanagement.entity.Lease;
import com.katcote.propertymanagement.entity.Property;
import com.katcote.propertymanagement.entity.Tenant;
import com.katcote.propertymanagement.repository.LandlordRepository;
import com.katcote.propertymanagement.repository.LeaseRepository;
import com.katcote.propertymanagement.repository.PropertyRepository;
import com.katcote.propertymanagement.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/leases")
public class LeaseController {

    @Autowired
    private LeaseRepository leaseRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private LandlordRepository landlordRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @PostMapping
    public Lease createLease(@RequestBody Lease lease) {
        System.out.println("🔍 Creating lease with landlordId: " + lease.getLandlordId());

        if (lease.getLandlordId() != null) {
            Optional<Landlord> landlordOpt = landlordRepository.findById(lease.getLandlordId());
            if (landlordOpt.isPresent()) {
                lease.setLandlord(landlordOpt.get());
                lease.setLandlordId(landlordOpt.get().getId());
                System.out.println("✅ Linked to landlord: " + landlordOpt.get().getId());
            } else {
                System.out.println("❌ Landlord not found: " + lease.getLandlordId());
            }
        }

        if (lease.getTenantId() != null) {
            Optional<Tenant> tenantOpt = tenantRepository.findById(lease.getTenantId());
            if (tenantOpt.isPresent()) {
                lease.setTenant(tenantOpt.get());
                lease.setTenantId(tenantOpt.get().getId());
                System.out.println("✅ Linked to tenant: " + tenantOpt.get().getId());
            } else {
                System.out.println("❌ Tenant not found: " + lease.getTenantId());
            }
        }

        if (lease.getPropertyId() != null) {
            Optional<Property> propertyOpt = propertyRepository.findById(lease.getPropertyId());
            if (propertyOpt.isPresent()) {
                lease.setProperty(propertyOpt.get());
                lease.setPropertyId(propertyOpt.get().getId());
                System.out.println("✅ Linked to property: " + propertyOpt.get().getId());
            } else {
                System.out.println("❌ Property not found: " + lease.getProperty());
            }
        }

        Lease saved = leaseRepository.save(lease);

        return saved;
    }

    @GetMapping
    public List<Lease> getAllLeases() {
        return leaseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lease> getLease(@PathVariable Long id) {
        Optional<Lease> lease = leaseRepository.findById(id);
        return lease.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lease> updateLease(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<Lease> optionalLease = leaseRepository.findById(id);
        if (optionalLease.isPresent()) {
            Lease existingLease = optionalLease.get();

            for (Map.Entry<String, Object> entry : updates.entrySet()) {
                String fieldName = entry.getKey();
                Object value = entry.getValue();

                switch (fieldName) {
                    case "endDate":
                        if (value instanceof String) {
                            existingLease.setEndDate(LocalDate.parse((String) value));
                        }
                        break;
                    case "propertyId":
                        Long propertyId = ((Number) value).longValue();
                        Optional<Property> propertyOpt = propertyRepository.findById(propertyId);
                        if (propertyOpt.isPresent()) {
                            existingLease.setProperty(propertyOpt.get());
                            existingLease.setPropertyId(propertyOpt.get().getId());
                        } else {
                            System.out.println("❌ Property not found with ID: " + propertyId);
                        }
                        break;
                    case "landlordId":
                        Long landlordId = ((Number) value).longValue();
                        Optional<Landlord> landlordOpt = landlordRepository.findById(landlordId);
                        if (landlordOpt.isPresent()) {
                            existingLease.setLandlord(landlordOpt.get());
                            existingLease.setLandlordId(landlordOpt.get().getId());
                        } else {
                            System.out.println("❌ Landlord not found with ID: " + landlordId);
                        }
                        break;
                    case "tenantId":
                        Long tenantId = ((Number) value).longValue();
                        Optional<Tenant> tenantOpt = tenantRepository.findById(tenantId);
                        if (tenantOpt.isPresent()) {
                            existingLease.setTenant(tenantOpt.get());
                            existingLease.setTenantId(tenantOpt.get().getId());
                        } else {
                            System.out.println("❌ Tenant not found with ID: " + tenantId);
                        }
                        break;
                    case "deposit":
                        existingLease.setDeposit((Integer) value);
                        break;
                    case "lateFee":
                        existingLease.setLateFee((Integer) value);
                        break;
                    case "gracePeriodDays":
                        existingLease.setGracePeriodDays((Integer) value);
                        break;
                    case "status":
                        existingLease.setStatus((String) value);
                        break;
                    case "autoRenew":
                        existingLease.setAutoRenew((Boolean) value);
                        break;
                    case "notes":
                        existingLease.setNotes((String) value);
                        break;
                    case "signedDate":
                        if (value instanceof String) {
                            existingLease.setSignedDate(LocalDate.parse((String) value));
                        }
                        break;
                    default:
                        System.out.println("⚠️  Unknown field: " + fieldName);
                }
            }

            Lease saved = leaseRepository.save(existingLease);
            return ResponseEntity.ok(saved);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLease(@PathVariable Long id) {
        if (leaseRepository.existsById(id)) {
            leaseRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}