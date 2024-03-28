package com.eduardo.agregadorinvestimentos.controller;

import com.eduardo.agregadorinvestimentos.dto.AccountStockResponseDto;
import com.eduardo.agregadorinvestimentos.dto.AssociateAccountStockDto;
import com.eduardo.agregadorinvestimentos.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@Tag(name = "accounts", description = "API para gerenciamento de contas")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService service) {
        this.accountService = service;
    }

    @Operation(summary = "Associar ação a uma conta", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ação associada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @PostMapping("/{accountId}/stocks")
    public ResponseEntity<Void> associateStock(@PathVariable("accountId") String accountId,
                                               @RequestBody AssociateAccountStockDto dto) {

        accountService.associateStock(accountId, dto);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Listar ações associadas a uma conta", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ações recuperada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    })
    @GetMapping("/{accountId}/stocks")
    public ResponseEntity<List<AccountStockResponseDto>> associateStock(@PathVariable("accountId") String accountId) {
        var stocks = accountService.listStocks(accountId);

        return ResponseEntity.ok(stocks);
    }

}
