package com.katcote.propertymanagement.controller;

import com.katcote.propertymanagement.entity.Property;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    private List<Property> properties = new ArrayList<>();

    @PostMapping
    public Property createProperty() {
        Property property = new Property();
        properties.add(property);
        return property;
    }

    @GetMapping
    public List<Property> getAllProperties() {
        return properties;
    }

    @GetMapping("/{id}")
    public Property getProperty(@PathVariable Long id) {
        return properties.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PutMapping("/{id}")
    public Property updateProperty(@PathVariable Long id) {
        Property property = getProperty(id);
        if (property != null) {
            // Логика обновления будет добавлена позже
            return property;
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteProperty(@PathVariable Long id) {
        properties.removeIf(p -> p.getId().equals(id));
    }
}
