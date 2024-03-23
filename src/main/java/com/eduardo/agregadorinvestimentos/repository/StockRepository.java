package com.eduardo.agregadorinvestimentos.repository;

import com.eduardo.agregadorinvestimentos.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, String> {
}
