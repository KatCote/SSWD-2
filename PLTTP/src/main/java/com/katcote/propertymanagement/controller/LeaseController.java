package com.katcote.propertymanagement.controller;

import com.katcote.propertymanagement.entity.Lease;
import com.katcote.propertymanagement.repository.LeaseRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/leases")
public class LeaseController {

    @Autowired
    private LeaseRepository leaseRepository;

    @PostMapping
    public Lease createLease(@RequestBody Lease lease) {
        return leaseRepository.save(lease);
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
    public ResponseEntity<Lease> updateLease(@PathVariable Long id, @RequestBody Lease leaseDetails) {
        Optional<Lease> optionalLease = leaseRepository.findById(id);
        if (optionalLease.isPresent()) {
            Lease existingLease = optionalLease.get();
            BeanUtils.copyProperties(leaseDetails, existingLease,"id", "leaseNumber", "startDate", "createdAt", "version");
            return ResponseEntity.ok(leaseRepository.save(existingLease));
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