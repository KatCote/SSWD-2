package com.katcote.propertymanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "landlords", indexes = {
        @Index(name = "idx_landlord_email", columnList = "email"),
        @Index(name = "idx_landlord_last_name", columnList = "lastName"),
        @Index(name = "idx_landlord_active", columnList = "active")
    }
)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Landlord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "landlord", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Property> properties = new ArrayList<>();

    @OneToMany(mappedBy = "landlord", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Lease> leases = new ArrayList<>();

    @Column(name = "first_name", nullable = false, length = 32)
    private String firstName = "Unknown";

    @Column(name = "last_name", nullable = false, length = 32)
    private String lastName = "Unknown";

    @Column(name = "email", nullable = false, length = 150)
    private String email = "Unknown";

    @Column(name = "phone", length = 11, nullable = false)
    private String phone = "Unknown";

    @Column(name = "address", length = 255, nullable = false)
    private String address = "Unknown";

    @Column(name = "tax_id", length = 50, nullable = false)
    private String taxId = "Unknown";

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth = LocalDate.of(1, 1, 1);

    @Column(name = "emergency_contact", length = 255, nullable = false)
    private String emergencyContact = "Unknown";

    @Column(name = "notes", columnDefinition = "TEXT", nullable = false, length = 1024)
    private String notes = "None";

    @Column(name = "active", nullable = false)
    private Boolean active = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "version", nullable = false)
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

    public Landlord() {}

    public Long getId() { return id; }

    public void addProperty(Property property) {
        properties.add(property);
        property.setLandlord(this);
    }

    public void removeProperty(Property property) {
        properties.remove(property);
        property.setLandlord(null);
    }

    public void addLease(Lease lease) {
        leases.add(lease);
        lease.setLandlord(this);
    }

    public void removeLease(Lease lease) {
        leases.remove(lease);
        lease.setLandlord(null);
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getTaxId() { return taxId; }
    public void setTaxId(String taxId) { this.taxId = taxId; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }
}