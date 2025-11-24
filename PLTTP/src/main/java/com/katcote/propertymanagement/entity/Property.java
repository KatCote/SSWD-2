package com.katcote.propertymanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "properties", indexes = {
        @Index(name = "idx_property_city", columnList = "city"),
        @Index(name = "idx_property_available", columnList = "available"),
        @Index(name = "idx_property_rent_amount", columnList = "rentAmount"),
        @Index(name = "idx_property_square_meters", columnList = "squareMeters")
    }
)
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address", nullable = false, length = 256)
    private String address;

    @Column(name = "city", nullable = false, length = 32)
    private String city;

    @Column(name = "state", nullable = false, length = 32)
    private String state;

    @Column(name = "zip_code", nullable = false, length = 8)
    private String zipCode;

    @Column(name = "property_type", length = 50, nullable = false)
    private String propertyType;

    @Column(name = "bedrooms", nullable = false)
    private Integer bedrooms;

    @Column(name = "bathrooms", nullable = false)
    private Integer bathrooms;

    @Column(name = "square_meters", nullable = false)
    private Integer squareMeters;

    @Column(name = "rent_amount", nullable = false)
    private Integer rentAmount;

    @Column(name = "deposit", nullable = false)
    private Integer deposit;

    @Column(name = "available", nullable = false)
    private Boolean available = true;

    @Column(name = "description", columnDefinition = "TEXT", length = 1024, nullable = false)
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "version")
    private Integer version;

    @PrePersist
    protected void onCreate()
    {
        address         = "Not specified";
        city            = "Not specified";
        state           = "Not specified";
        zipCode         = "Not specified";
        propertyType    = "Not specified";
        bedrooms        = 0;
        bathrooms       = 0;
        squareMeters    = 0;
        rentAmount      = 0;
        deposit         = 0;
        available       = false;
        description     = "Not specified";
        createdAt       = LocalDateTime.now();
        updatedAt       = LocalDateTime.now();
        version         = 0;
    }

    @PreUpdate
    protected void onUpdate()
    { updatedAt = LocalDateTime.now(); version = version + 1; }

    public Property() {}

    public Property
            (
            String address,
            String city,
            String state,
            String zipCode,
            String propertyType,
            Integer squareMeters,
            Integer rentAmount,
            Boolean available
            ){
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.propertyType = propertyType;
        this.squareMeters = squareMeters;
        this.rentAmount = rentAmount;
        this.available = available;
    }

    public Long getId() { return id; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    public String getPropertyType() { return propertyType; }
    public void setPropertyType(String propertyType) { this.propertyType = propertyType; }

    public Integer getBedrooms() { return bedrooms; }
    public void setBedrooms(Integer bedrooms) { this.bedrooms = bedrooms; }

    public Integer getBathrooms() { return bathrooms; }
    public void setBathrooms(Integer bathrooms) { this.bathrooms = bathrooms; }

    public Integer getSquareMeters() { return squareMeters; }
    public void setSquareMeters(Integer squareMeters) { this.squareMeters = squareMeters; }

    public Integer getRentAmount() { return rentAmount; }
    public void setRentAmount(Integer rentAmount) { this.rentAmount = rentAmount; }

    public Integer getDeposit() { return deposit; }
    public void setDeposit(Integer deposit) { this.deposit = deposit; }

    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }
}