package com.eduardo.agregadorinvestimentos.repository;

import com.eduardo.agregadorinvestimentos.entity.AccountStock;
import com.eduardo.agregadorinvestimentos.entity.AccountStockId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
}
