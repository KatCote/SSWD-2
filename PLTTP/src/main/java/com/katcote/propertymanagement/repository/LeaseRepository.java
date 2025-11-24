package com.katcote.propertymanagement.repository;

import com.katcote.propertymanagement.entity.Lease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaseRepository extends JpaRepository<Lease, Long> {

    // Найти аренды по дате начала
    List<Lease> findByStartDate(LocalDate startDate);

    // Найти активные аренды (текущая дата между startDate и endDate)
    @Query("SELECT l FROM Lease l WHERE l.startDate <= :currentDate AND l.endDate >= :currentDate")
    List<Lease> findActiveLeases(@Param("currentDate") LocalDate currentDate);

    // Найти истекшие аренды
    @Query("SELECT l FROM Lease l WHERE l.endDate < :currentDate")
    List<Lease> findExpiredLeases(@Param("currentDate") LocalDate currentDate);

    // Найти будущие аренды
    @Query("SELECT l FROM Lease l WHERE l.startDate > :currentDate")
    List<Lease> findFutureLeases(@Param("currentDate") LocalDate currentDate);

    // Найти аренды с ежемесячной платой больше указанной
    List<Lease> findByMonthlyRentGreaterThan(Double minRent);

    // Найти аренды с ежемесячной платой меньше указанной
    List<Lease> findByMonthlyRentLessThan(Double minRent);

    // Найти аренды по диапазону дат
    List<Lease> findByStartDateBetween(LocalDate start, LocalDate end);

    // Найти аренды с залогом
    List<Lease> findBySecurityDepositIsNotNull();

    // Найти аренды без залога
    List<Lease> findBySecurityDepositIsNull();
}