package com.katcote.propertymanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "leases", indexes = {
        @Index(name = "idx_lease_lease_number", columnList = "leaseNumber"),
        @Index(name = "idx_lease_start_date", columnList = "startDate"),
        @Index(name = "idx_lease_end_date", columnList = "endDate"),
        @Index(name = "idx_lease_status", columnList = "status")
    }
)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Lease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @Transient
    private Long propertyId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @Transient
    private Long tenantId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landlord_id", nullable = false)
    private Landlord landlord;

    @Transient
    private Long landlordId;

    @Column(name = "lease_number", unique = true, length = 50, nullable = false)
    private final String leaseNumber = LeaseCodeGenerator.generateLeaseCode();

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate = LocalDate.of(1, 1, 1);

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate = LocalDate.of(1, 1, 1);

    @Column(name = "deposit", nullable = false)
    private Integer deposit = 0;

    @Column(name = "late_fee", nullable = false)
    private Integer lateFee = 0;

    @Column(name = "grace_period_days", nullable = false)
    private Integer gracePeriodDays = 0;

    @Column(name = "status", nullable = false, length = 20)
    private String status = "INACTIVE";

    @Column(name = "auto_renew", nullable = false)
    private Boolean autoRenew = false;

    @Column(name = "notes", columnDefinition = "TEXT", nullable = false, length = 1024)
    private String notes = "None";

    @Column(name = "signed_date", nullable = false)
    private LocalDate signedDate = LocalDate.of(1, 1, 1);

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

    @PostLoad
    private void onLoad() {
        if (property != null) {
            this.propertyId = property.getId();
        }
        if (landlord != null) {
            this.landlordId = landlord.getId();
        }
        if (tenant != null) {
            this.tenantId = tenant.getId();
        }
    }

    public Lease() {}

    public Long getId() { return id; }


    public Property getProperty() { return property; }
    public void setProperty(Property property) { this.property = property; }

    public Long getPropertyId() { return propertyId; }
    public void setPropertyId(Long propertyId) { this.propertyId = propertyId; }

    public Tenant getTenant() { return tenant; }
    public void setTenant(Tenant tenant) { this.tenant = tenant; }

    public Long getTenantId() { return tenantId; }
    public void setTenantId(Long tenantId) { this.tenantId = tenantId; }

    public Landlord getLandlord() { return landlord; }
    public void setLandlord(Landlord landlord) { this.landlord = landlord; }

    public Long getLandlordId() { return landlordId; }
    public void setLandlordId(Long landlordId) { this.landlordId = landlordId; }


    public String getLeaseNumber() { return leaseNumber; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Integer getDeposit() { return deposit; }
    public void setDeposit(Integer deposit) { this.deposit = deposit; }

    public Integer getLateFee() { return lateFee; }
    public void setLateFee(Integer lateFee) { this.lateFee = lateFee; }

    public Integer getGracePeriodDays() { return gracePeriodDays; }
    public void setGracePeriodDays(Integer gracePeriodDays) { this.gracePeriodDays = gracePeriodDays; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Boolean getAutoRenew() { return autoRenew; }
    public void setAutoRenew(Boolean autoRenew) { this.autoRenew = autoRenew; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDate getSignedDate() { return signedDate; }
    public void setSignedDate(LocalDate signedDate) { this.signedDate = signedDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }

    public static class LeaseCodeGenerator {
        public static String generateLeaseCode() {
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();
            return "LEASE-" + date.toString() + "-" + Math.abs(time.hashCode()) + "-" + System.currentTimeMillis();
        }
    }
}