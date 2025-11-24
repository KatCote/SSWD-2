package com.katcote.propertymanagement.repository;

import com.katcote.propertymanagement.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    // Найти все доступные свойства
    List<Property> findByAvailableTrue();

    // Найти свойства по городу
    List<Property> findByCity(String city);

    // Найти свойства по городу и доступности
    List<Property> findByCityAndAvailableTrue(String city);

    // Найти свойства с ценой аренды меньше указанной
    List<Property> findByRentAmountLessThan(Double maxRent);

    // Найти свойства с ценой аренды в диапазоне
    List<Property> findByRentAmountBetween(Double minRent, Double maxRent);

    // Кастомный запрос для поиска по адресу (частичное совпадение)
    @Query("SELECT p FROM Property p WHERE LOWER(p.address) LIKE LOWER(CONCAT('%', :address, '%'))")
    List<Property> findByAddressContainingIgnoreCase(@Param("address") String address);

    // Проверить существование свойства по адресу
    boolean existsByAddressAndCityAndState(String address, String city, String state);
}