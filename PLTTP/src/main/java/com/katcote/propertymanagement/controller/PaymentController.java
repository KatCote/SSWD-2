package com.katcote.propertymanagement.controller;

import com.katcote.propertymanagement.entity.Payment;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private List<Payment> payments = new ArrayList<>();

    @PostMapping
    public Payment createPayment() {
        Payment payment = new Payment();
        payments.add(payment);
        return payment;
    }

    @GetMapping
    public List<Payment> getAllPayments() {
        return payments;
    }

    @GetMapping("/{id}")
    public Payment getPayment(@PathVariable Long id) {
        return payments.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PutMapping("/{id}")
    public Payment updatePayment(@PathVariable Long id) {
        Payment payment = getPayment(id);
        if (payment != null) {
            // Логика обновления будет добавлена позже
            return payment;
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deletePayment(@PathVariable Long id) {
        payments.removeIf(p -> p.getId().equals(id));
    }
}
