package com.eduardo.agregadorinvestimentos.controller;

import com.eduardo.agregadorinvestimentos.entity.Stock;
import com.eduardo.agregadorinvestimentos.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stocks")
public class StockController {

    private final StockService service;

    public StockController(StockService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody Stock stock){
        service.createStock(stock);
        return ResponseEntity.ok().build();
    }
}
