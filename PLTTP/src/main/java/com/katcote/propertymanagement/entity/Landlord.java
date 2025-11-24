package com.katcote.propertymanagement.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "landlords", indexes = {
        @Index(name = "idx_landlord_email", columnList = "email"),
        @Index(name = "idx_landlord_last_name", columnList = "lastName"),
        @Index(name = "idx_landlord_active", columnList = "active")
    }
)
public class Landlord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 32)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 32)
    private String lastName;

    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @Column(name = "phone", length = 11, nullable = false)
    private String phone;

    @Column(name = "address", length = 255, nullable = false)
    private String address;

    @Column(name = "tax_id", length = 50, nullable = false)
    private String taxId;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "emergency_contact", length = 255, nullable = false)
    private String emergencyContact;

    @Column(name = "notes", columnDefinition = "TEXT", nullable = false, length = 1024)
    private String notes;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "version")
    private Integer version;

    @PrePersist
    protected void onCreate()
    {
        firstName         = "Unknown";
        lastName          = "Unknown";
        email             = "Unknown";
        phone             = "Unknown";
        address           = "Unknown";
        taxId             = "Unknown";
        dateOfBirth       = LocalDate.of(1, 1, 1);
        emergencyContact  = "Unknown";
        notes             = "None";
        active            = false;
        createdAt         = LocalDateTime.now();
        updatedAt         = LocalDateTime.now();
        version           = 0;
    }

    @PreUpdate
    protected void onUpdate()
    { updatedAt = LocalDateTime.now(); version = version + 1; }

    public Landlord() {}

    public Landlord
        (
            String firstName,
            String lastName,
            LocalDate dateOfBirth,
            String taxId,
            String email,
            String phone,
            Boolean active
        ){
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.taxId = taxId;
        this.email = email;
        this.phone = phone;
        this.active = active;
    }

    public Long getId() { return id; }

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