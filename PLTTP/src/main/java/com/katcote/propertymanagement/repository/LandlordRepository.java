package com.katcote.propertymanagement.repository;

import com.katcote.propertymanagement.entity.Landlord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LandlordRepository extends JpaRepository<Landlord, Long> {

    // Найти арендодателя по email
    Optional<Landlord> findByEmail(String email);

    // Найти арендодателей по имени
    List<Landlord> findByFirstName(String firstName);

    // Найти арендодателей по фамилии
    List<Landlord> findByLastName(String lastName);

    // Найти арендодателей по имени и фамилии
    List<Landlord> findByFirstNameAndLastName(String firstName, String lastName);

    // Кастомный запрос для поиска по имени или фамилии
    @Query("SELECT l FROM Landlord l WHERE LOWER(l.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(l.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Landlord> findByFirstNameOrLastNameContainingIgnoreCase(@Param("name") String name);

    // Проверить существование арендодателя по email
    boolean existsByEmail(String email);

    // Найти арендодателей с телефоном
    List<Landlord> findByPhoneIsNotNull();
}