package com.eduardo.agregadorinvestimentos.controller;

import com.eduardo.agregadorinvestimentos.dto.AssociateAccountStockDto;
import com.eduardo.agregadorinvestimentos.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService service) {
        this.accountService = service;
    }

    @PostMapping("/{accountId}/stocks")
    public ResponseEntity<Void> associateStock(@PathVariable("accountId") String accountId,
                                               @RequestBody AssociateAccountStockDto dto) {

        accountService.associateStock(accountId, dto);

        return ResponseEntity.ok().build();
    }

}
