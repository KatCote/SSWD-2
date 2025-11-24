package com.katcote.propertymanagement.repository;

import com.katcote.propertymanagement.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Найти платежи по дате
    List<Payment> findByPaymentDate(LocalDate paymentDate);

    // Найти платежи по методу оплаты
    List<Payment> findByMethod(String method);

    // Найти платежи по диапазону дат
    List<Payment> findByPaymentDateBetween(LocalDate startDate, LocalDate endDate);

    // Найти платежи с суммой больше указанной
    List<Payment> findByAmountGreaterThan(Double minAmount);

    // Найти платежи с суммой меньше указанной
    List<Payment> findByAmountLessThan(Double minAmount);

    // Найти платежи с суммой в диапазоне
    List<Payment> findByAmountBetween(Double minAmount, Double maxAmount);

    // Найти платежи с описанием
    List<Payment> findByDescriptionIsNotNull();

    // Кастомный запрос для поиска платежей по части описания
    @Query("SELECT p FROM Payment p WHERE LOWER(p.description) LIKE LOWER(CONCAT('%', :description, '%'))")
    List<Payment> findByDescriptionContainingIgnoreCase(@Param("description") String description);

    // Подсчитать общую сумму платежей за период
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.paymentDate BETWEEN :startDate AND :endDate")
    Double getTotalAmountBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // Найти платежи по методу оплаты и дате
    List<Payment> findByMethodAndPaymentDate(String method, LocalDate paymentDate);
}