package com.katcote.propertymanagement.controller;

import com.katcote.propertymanagement.entity.Landlord;
import com.katcote.propertymanagement.entity.Property;
import com.katcote.propertymanagement.repository.LandlordRepository;
import com.katcote.propertymanagement.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private LandlordRepository landlordRepository;

    @PostMapping
    public Property createProperty(@RequestBody Property property) {
        System.out.println("🔍 Creating property with landlordId: " + property.getLandlordId());

        if (property.getLandlordId() != null) {
            Optional<Landlord> landlordOpt = landlordRepository.findById(property.getLandlordId());
            if (landlordOpt.isPresent()) {
                property.setLandlord(landlordOpt.get());
                property.setLandlordId(landlordOpt.get().getId());
                System.out.println("✅ Linked to landlord: " + landlordOpt.get().getId());
            } else {
                System.out.println("❌ Landlord not found: " + property.getLandlordId());
            }
        }

        Property saved = propertyRepository.save(property);
        System.out.println("💾 Saved property ID: " + saved.getId() + ", landlord_id: " +
                (saved.getLandlord() != null ? saved.getLandlord().getId() : "null"));

        return saved;
    }

    @GetMapping
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Property> getProperty(@PathVariable Long id) {
        Optional<Property> property = propertyRepository.findById(id);
        return property.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Property> updateProperty(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<Property> optionalProperty = propertyRepository.findById(id);
        if (optionalProperty.isPresent()) {
            Property existingProperty = optionalProperty.get();

            for (Map.Entry<String, Object> entry : updates.entrySet()) {
                String fieldName = entry.getKey();
                Object value = entry.getValue();

                switch (fieldName) {
                    case "landlordId":
                        Long landlordId = ((Number) value).longValue();
                        Optional<Landlord> landlordOpt = landlordRepository.findById(landlordId);
                        if (landlordOpt.isPresent()) {
                            existingProperty.setLandlord(landlordOpt.get());
                            existingProperty.setLandlordId(landlordOpt.get().getId());
                            System.out.println("✅ Linked property to landlord ID: " + landlordId);
                        } else {
                            System.out.println("❌ Landlord not found with ID: " + landlordId);
                        }
                        break;
                    case "address":
                        existingProperty.setAddress((String) value);
                        break;
                    case "city":
                        existingProperty.setCity((String) value);
                        break;
                    case "state":
                        existingProperty.setState((String) value);
                        break;
                    case "zipCode":
                        existingProperty.setZipCode((String) value);
                        break;
                    case "propertyType":
                        existingProperty.setPropertyType((String) value);
                        break;
                    case "bedrooms":
                        existingProperty.setBedrooms((Integer) value);
                        break;
                    case "bathrooms":
                        existingProperty.setBathrooms((Integer) value);
                        break;
                    case "squareMeters":
                        existingProperty.setSquareMeters((Integer) value);
                        break;
                    case "rentAmount":
                        existingProperty.setRentAmount((Integer) value);
                        break;
                    case "deposit":
                        existingProperty.setDeposit((Integer) value);
                        break;
                    case "available":
                        existingProperty.setAvailable((Boolean) value);
                        break;
                    case "description":
                        existingProperty.setDescription((String) value);
                        break;
                    default:
                        System.out.println("⚠️  Unknown field: " + fieldName);
                }
            }

            Property saved = propertyRepository.save(existingProperty);
            return ResponseEntity.ok(saved);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        if (propertyRepository.existsById(id)) {
            propertyRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}