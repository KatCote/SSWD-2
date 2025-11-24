package com.katcote.propertymanagement.controller;

import com.katcote.propertymanagement.entity.Lease;
import com.katcote.propertymanagement.entity.Payment;
import com.katcote.propertymanagement.repository.LeaseRepository;
import com.katcote.propertymanagement.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private LeaseRepository leaseRepository;

    @PostMapping
    public Payment createPayment(@RequestBody Payment payment) {
        System.out.println("🔍 Creating property with leaseId: " + payment.getLeaseId());

        if (payment.getLeaseId() != null) {
            Optional<Lease> leaseOpt = leaseRepository.findById(payment.getLeaseId());
            if (leaseOpt.isPresent()) {
                payment.setLease(leaseOpt.get());
                payment.setLeaseId(leaseOpt.get().getId());
                System.out.println("✅ Linked to lease: " + leaseOpt.get().getId());
            } else {
                System.out.println("❌ Lease not found: " + payment.getLeaseId());
            }
        }

        Payment saved = paymentRepository.save(payment);
        System.out.println("💾 Saved property ID: " + saved.getId() + ", lease_id: " +
                (saved.getLease() != null ? saved.getLease().getId() : "null"));

        return saved;
    }

    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPayment(@PathVariable Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);
        return payment.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<Payment> optionalPayment = paymentRepository.findById(id);
        if (optionalPayment.isPresent()) {
            Payment existingPayment = optionalPayment.get();

            for (Map.Entry<String, Object> entry : updates.entrySet()) {
                String fieldName = entry.getKey();
                Object value = entry.getValue();

                switch (fieldName) {
                    case "leaseId":
                        Long leaseId = ((Number) value).longValue();
                        Optional<Lease> leaseOpt = leaseRepository.findById(leaseId);
                        if (leaseOpt.isPresent()) {
                            existingPayment.setLease(leaseOpt.get());
                            existingPayment.setLeaseId(leaseOpt.get().getId());
                            System.out.println("✅ Linked payment to lease id: " + leaseId);
                        } else {
                            System.out.println("❌ Lease not found with id: " + leaseId);
                        }
                        break;
                    case "amount":
                        existingPayment.setAmount((Integer) value);
                        break;
                    case "dueDate":
                        // Предполагаем, что dueDate приходит в формате строки ISO (например, "2024-01-15")
                        LocalDate dueDate = LocalDate.parse((String) value);
                        existingPayment.setDueDate(dueDate);
                        break;
                    case "method":
                        existingPayment.setMethod((String) value);
                        break;
                    case "status":
                        existingPayment.setStatus((String) value);
                        break;
                    case "description":
                        existingPayment.setDescription((String) value);
                        break;
                    case "notes":
                        existingPayment.setNotes((String) value);
                        break;
                    default:
                        System.out.println("⚠️  Unknown field: " + fieldName);
                }
            }

            Payment saved = paymentRepository.save(existingPayment);
            return ResponseEntity.ok(saved);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        if (paymentRepository.existsById(id)) {
            paymentRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}