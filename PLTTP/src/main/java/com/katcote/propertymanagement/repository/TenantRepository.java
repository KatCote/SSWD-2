package com.katcote.propertymanagement.repository;

import com.katcote.propertymanagement.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

    // Найти арендатора по email
    Optional<Tenant> findByEmail(String email);

    // Найти арендаторов по имени
    List<Tenant> findByFirstName(String firstName);

    // Найти арендаторов по фамилии
    List<Tenant> findByLastName(String lastName);

    // Найти арендаторов по имени и фамилии
    List<Tenant> findByFirstNameAndLastName(String firstName, String lastName);

    // Кастомный запрос для поиска по имени или фамилии
    @Query("SELECT t FROM Tenant t WHERE LOWER(t.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(t.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Tenant> findByFirstNameOrLastNameContainingIgnoreCase(@Param("name") String name);

    // Проверить существование арендатора по email
    boolean existsByEmail(String email);

    // Найти арендаторов с телефоном
    List<Tenant> findByPhoneIsNotNull();

    // Подсчитать количество арендаторов
    long count();
}