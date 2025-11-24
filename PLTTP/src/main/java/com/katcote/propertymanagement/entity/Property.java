package com.katcote.propertymanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landlord_id", nullable = false)
    private Landlord landlord;

    @Transient
    private Long landlordId;

    @Column(name = "address", columnDefinition = "TEXT", nullable = false)
    private String address = "Not specified";

    @Column(name = "city", columnDefinition = "TEXT", nullable = false)
    private String city = "Not specified";

    @Column(name = "state", columnDefinition = "TEXT", nullable = false)
    private String state = "Not specified";

    @Column(name = "zip_code", columnDefinition = "TEXT", nullable = false)
    private String zipCode = "Not specified";

    @Column(name = "property_type", columnDefinition = "TEXT", nullable = false)
    private String propertyType = "Not specified";

    @Column(name = "bedrooms", nullable = false)
    private Integer bedrooms = 0;

    @Column(name = "bathrooms", nullable = false)
    private Integer bathrooms = 0;

    @Column(name = "square_meters", nullable = false)
    private Integer squareMeters = 0;

    @Column(name = "rent_amount", nullable = false)
    private Integer rentAmount = 0;

    @Column(name = "deposit", nullable = false)
    private Integer deposit = 0;

    @Column(name = "available", nullable = false)
    private Boolean available = false;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description = "Not specified";

    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "version")
    private Integer version;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        updatedAt = LocalDateTime.now();
        if (version == null) {
            version = 0;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        if (version != null) {
            version = version + 1;
        } else {
            version = 0;
        }
    }

    @PostLoad
    private void onLoad() {
        if (landlord != null) {
            this.landlordId = landlord.getId();
        }
    }

    public Property() {}

    public Long getId() { return id; }


    public Landlord getLandlord() { return landlord; }
    public void setLandlord(Landlord landlord) { this.landlord = landlord; }

    public Long getLandlordId() { return landlordId; }
    public void setLandlordId(Long landlordId) { this.landlordId = landlordId; }


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