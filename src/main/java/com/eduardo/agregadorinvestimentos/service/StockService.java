package com.eduardo.agregadorinvestimentos.service;

import com.eduardo.agregadorinvestimentos.entity.Stock;
import com.eduardo.agregadorinvestimentos.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private final StockRepository repository;

    public StockService(StockRepository repository) {
        this.repository = repository;
    }


    public Stock createStock(Stock stock) {
        return repository.save(stock);
    }
}
