package com.katcote.propertymanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "landlords")
public class Landlord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Поля будут добавлены позже

    public Landlord() {}

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}
