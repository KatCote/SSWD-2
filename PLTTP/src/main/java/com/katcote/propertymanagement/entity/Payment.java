package com.katcote.propertymanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "payments", indexes = {
        @Index(name = "idx_payment_number", columnList = "paymentNumber"),
        @Index(name = "idx_payment_date", columnList = "paymentDate"),
        @Index(name = "idx_payment_status", columnList = "status"),
        @Index(name = "idx_payment_method", columnList = "method")
    }
)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_number", unique = true, length = 50)
    private String paymentNumber;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    @Column(name = "payment_time", nullable = false)
    private LocalTime paymentTime;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "method", nullable = false, length = 20)
    private String method; // CASH, CREDIT_CARD, DEBIT_CARD, BANK_TRANSFER, CHECK

    @Column(name = "status", nullable = false, length = 20)
    private String status = "PENDING"; // PENDING, COMPLETED, FAILED, REFUNDED

    @Column(name = "lease_number", length = 100, nullable = false)
    private String leaseNumber;

    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @Column(name = "landlord_id", nullable = false)
    private Long landlordId;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "notes", columnDefinition = "TEXT", nullable = false)
    private String notes;

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
        paymentNumber   = "PAY-" + id + "-" + LocalDate.now() + "-" + LocalTime.now().hashCode() + "-" + System.currentTimeMillis();
        amount          = 0;
        paymentDate     = LocalDate.of(1, 1, 1);
        paymentTime     = LocalTime.of(1, 1);
        dueDate         = LocalDate.of(1, 1, 1);
        method          = "UNKNOWN";
        status          = "PENDING";
        leaseNumber     = "Unknown";
        tenantId        = 0L;
        landlordId      = 0L;
        description     = "None";
        notes           = "None";
        createdAt       = LocalDateTime.now();
        updatedAt       = LocalDateTime.now();
        version         = 0;
    }

    @PreUpdate
    protected void onUpdate()
    { updatedAt = LocalDateTime.now(); version = version + 1; }

    // Конструкторы
    public Payment() {}

    public Payment
        (
            Integer amount,
            LocalDate paymentDate,
            LocalTime paymentTime,
            LocalDate dueDate,
            String method,
            String leaseNumber,
            Long tenantId,
            Long landlordId
        ){
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentTime = paymentTime;
        this.dueDate = dueDate;
        this.method = method;
        this.leaseNumber = leaseNumber;
        this.tenantId = tenantId;
        this.landlordId = landlordId;
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }

    public String getPaymentNumber() { return paymentNumber; }

    public Integer getAmount() { return amount; }
    public void setAmount(Integer amount) { this.amount = amount; }

    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }

    public LocalTime getPaymentTime() { return paymentTime; }
    public void setPaymentTime(LocalTime paymentDate) { this.paymentTime = paymentTime; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getLeaseNumber() { return leaseNumber; }
    public void setLeaseNumber(String leaseNumber) { this.leaseNumber = leaseNumber; }

    public Long getTenantId() { return tenantId; }
    public void setTenantId(Long tenantId) { this.tenantId = tenantId; }

    public Long getLandlordId() { return landlordId; }
    public void setLandlordId(Long landlordId) { this.landlordId = landlordId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }
}