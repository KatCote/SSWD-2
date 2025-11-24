package com.katcote.propertymanagement.controller;

import com.katcote.propertymanagement.entity.Landlord;
import com.katcote.propertymanagement.repository.LandlordRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/landlords")
public class LandlordController {

    @Autowired
    private LandlordRepository landlordRepository;

    @PostMapping
    public Landlord createLandlord(@RequestBody Landlord landlord) {
        return landlordRepository.save(landlord);
    }

    @GetMapping
    public List<Landlord> getAllLandlords() {
        return landlordRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Landlord> getLandlord(@PathVariable Long id) {
        Optional<Landlord> landlord = landlordRepository.findById(id);
        return landlord.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Landlord> updateLandlord(@PathVariable Long id, @RequestBody Landlord landlordDetails) {
        Optional<Landlord> optionalLandlord = landlordRepository.findById(id);
        if (optionalLandlord.isPresent()) {
            Landlord existingLandlord = optionalLandlord.get();
            BeanUtils.copyProperties(landlordDetails, existingLandlord,"id", "createdAt", "version");
            return ResponseEntity.ok(landlordRepository.save(existingLandlord));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLandlord(@PathVariable Long id) {
        if (landlordRepository.existsById(id)) {
            landlordRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}