package com.katcote.propertymanagement.repository;

import com.katcote.propertymanagement.entity.Lease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaseRepository extends JpaRepository<Lease, Long> {}