package com.katcote.propertymanagement.controller;

import com.katcote.propertymanagement.entity.Landlord;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/landlords")
public class LandlordController {

    private List<Landlord> landlords = new ArrayList<>();

    @PostMapping
    public Landlord createLandlord() {
        Landlord landlord = new Landlord();
        landlords.add(landlord);
        return landlord;
    }

    @GetMapping
    public List<Landlord> getAllLandlords() {
        return landlords;
    }

    @GetMapping("/{id}")
    public Landlord getLandlord(@PathVariable Long id) {
        return landlords.stream()
                .filter(l -> l.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PutMapping("/{id}")
    public Landlord updateLandlord(@PathVariable Long id) {
        Landlord landlord = getLandlord(id);
        if (landlord != null) {
            // Логика обновления будет добавлена позже
            return landlord;
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteLandlord(@PathVariable Long id) {
        landlords.removeIf(l -> l.getId().equals(id));
    }
}
