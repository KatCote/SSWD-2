package com.katcote.propertymanagement.entity;

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
public class Lease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lease_number", unique = true, length = 50, nullable = false)
    private String leaseNumber;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "monthly_rent", nullable = false)
    private Integer monthlyRent;

    @Column(name = "deposit", nullable = false)
    private Integer deposit;

    @Column(name = "late_fee", nullable = false)
    private Integer lateFee;

    @Column(name = "grace_period_days", nullable = false)
    private Integer gracePeriodDays;

    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @Column(name = "landlord_id", nullable = false)
    private Long landlordId;

    @Column(name = "status", nullable = false, length = 20)
    private String status = "ACTIVE";

    @Column(name = "auto_renew", nullable = false)
    private Boolean autoRenew = false;

    @Column(name = "notes", columnDefinition = "TEXT", nullable = false, length = 1024)
    private String notes;

    @Column(name = "signed_date", nullable = false)
    private LocalDate signedDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "version")
    private Long version;

    @PrePersist
    protected void onCreate()
    {
        leaseNumber         = "LEASE-" + id + "-" + LocalDate.now() + "-" + LocalTime.now().hashCode() + "-" + System.currentTimeMillis();
        startDate           = LocalDate.of(1, 1, 1);
        endDate             = LocalDate.of(1, 1, 1);
        monthlyRent         = 0;
        deposit             = 0;
        lateFee             = 0;
        gracePeriodDays     = 0;
        status              = "INACTIVE";
        autoRenew           = false;
        notes               = "None";
        signedDate          = LocalDate.of(1, 1, 1);
        createdAt           = LocalDateTime.now();
        updatedAt           = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate()
    { updatedAt = LocalDateTime.now(); version = version + 1; }

    public Lease() {}

    public Lease
        (
            LocalDate startDate,
            LocalDate endDate,
            Integer monthlyRent,
            String status
        ){
        this.startDate = startDate;
        this.endDate = endDate;
        this.monthlyRent = monthlyRent;
        this.status = status;
    }

    public Long getId() { return id; }

    public String getLeaseNumber() { return leaseNumber; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Integer getMonthlyRent() { return monthlyRent; }
    public void setMonthlyRent(Integer monthlyRent) { this.monthlyRent = monthlyRent; }

    public Integer getDeposit() { return deposit; }
    public void setDeposit(Integer deposit) { this.deposit = deposit; }

    public Integer getLateFee() { return lateFee; }
    public void setLateFee(Integer lateFee) { this.lateFee = lateFee; }

    public Integer getGracePeriodDays() { return gracePeriodDays; }
    public void setGracePeriodDays(Integer gracePeriodDays) { this.gracePeriodDays = gracePeriodDays; }

    public Long getTenantId() { return tenantId; }
    public void setTenantId(Long tenantId) { this.tenantId = tenantId; }

    public Long getLandlordId() { return landlordId; }
    public void setLandlordId(Long landlordId) { this.landlordId = landlordId; }

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

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }
}