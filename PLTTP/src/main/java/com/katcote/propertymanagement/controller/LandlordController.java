package com.katcote.propertymanagement.controller;

import com.katcote.propertymanagement.entity.Landlord;
import com.katcote.propertymanagement.repository.LandlordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/landlords")
public class LandlordController {

    @Autowired
    private LandlordRepository landlordRepository;

    @PostMapping
    public Landlord createLandlord(@RequestBody Landlord landlord) {
        if (landlord == null) { landlord = new Landlord(); }
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
    public ResponseEntity<Landlord> updateLandlord(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<Landlord> optionalLandlord = landlordRepository.findById(id);
        if (optionalLandlord.isPresent()) {
            Landlord existingLandlord = optionalLandlord.get();

            for (Map.Entry<String, Object> entry : updates.entrySet()) {
                String fieldName = entry.getKey();
                Object value = entry.getValue();

                switch (fieldName) {
                    case "firstName":
                        existingLandlord.setFirstName((String) value);
                        break;
                    case "lastName":
                        existingLandlord.setLastName((String) value);
                        break;
                    case "email":
                        existingLandlord.setEmail((String) value);
                        break;
                    case "phone":
                        existingLandlord.setPhone((String) value);
                        break;
                    case "address":
                        existingLandlord.setAddress((String) value);
                        break;
                    case "taxId":
                        existingLandlord.setTaxId((String) value);
                        break;
                    case "dateOfBirth":
                        if (value instanceof String)
                        { existingLandlord.setDateOfBirth(LocalDate.parse((String) value)); }
                        break;
                    case "emergencyContact":
                        existingLandlord.setEmergencyContact((String) value);
                        break;
                    case "notes":
                        existingLandlord.setNotes((String) value);
                        break;
                    case "active":
                        existingLandlord.setActive((Boolean) value);
                        break;
                    default:
                        System.out.println("⚠️  Unknown field: " + fieldName);
                }
            }

            Landlord saved = landlordRepository.save(existingLandlord);
            return ResponseEntity.ok(saved);
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