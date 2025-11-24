package com.katcote.propertymanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lease_id", nullable = false)
    private Lease lease;

    @Transient
    private Long leaseId;

    @Column(name = "payment_number", unique = true, length = 50, nullable = false)
    private final String paymentNumber = PaymentCodeGenerator.generatePaymentCode();

    @Column(name = "amount", nullable = false)
    private Integer amount = 0;

    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate = LocalDate.now();

    @Column(name = "payment_time", nullable = false)
    private LocalTime paymentTime = LocalTime.now();

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate = LocalDate.of(1, 1, 1);

    @Column(name = "method", nullable = false, length = 20)
    private String method = "UNKNOWN";

    @Column(name = "status", nullable = false, length = 20)
    private String status = "PENDING";

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description = "None";

    @Column(name = "notes", columnDefinition = "TEXT", nullable = false)
    private String notes = "None";

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
        if (lease != null) {
            this.leaseId = lease.getId();
        }
    }

    // Конструкторы
    public Payment() {}

    // Геттеры и сеттеры
    public Long getId() { return id; }


    public Lease getLease() { return lease; }
    public void setLease(Lease lease) { this.lease = lease; }

    public Long getLeaseId() { return leaseId; }
    public void setLeaseId(Long leaseId) { this.leaseId = leaseId; }


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

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }

    public static class PaymentCodeGenerator {
        public static String generatePaymentCode() {
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();
            return "PAY-" + date.toString() + "-" + Math.abs(time.hashCode()) + "-" + System.currentTimeMillis();
        }
    }
}