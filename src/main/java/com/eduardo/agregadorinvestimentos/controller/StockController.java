package com.eduardo.agregadorinvestimentos.controller;

import com.eduardo.agregadorinvestimentos.entity.Stock;
import com.eduardo.agregadorinvestimentos.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stocks")
@Tag(name = "stocks", description = "API para criar as ações que vão ser consumidas pela Account")
public class StockController {

    private final StockService service;

    public StockController(StockService service) {
        this.service = service;
    }

    @Operation(summary = "Criar ação", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ação criada com sucesso"),
    })
    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody Stock stock){
        service.createStock(stock);
        return ResponseEntity.ok().build();
    }
}
