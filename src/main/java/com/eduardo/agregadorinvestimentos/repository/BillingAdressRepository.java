package com.eduardo.agregadorinvestimentos.repository;

import com.eduardo.agregadorinvestimentos.entity.BillingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BillingAdressRepository extends JpaRepository<BillingAddress, UUID> {
}
