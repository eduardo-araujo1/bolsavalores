package com.eduardo.agregadorinvestimentos.service;

import com.eduardo.agregadorinvestimentos.client.BrapiClient;
import com.eduardo.agregadorinvestimentos.dto.AccountStockResponseDto;
import com.eduardo.agregadorinvestimentos.dto.AssociateAccountStockDto;
import com.eduardo.agregadorinvestimentos.entity.Account;
import com.eduardo.agregadorinvestimentos.entity.AccountStock;
import com.eduardo.agregadorinvestimentos.entity.AccountStockId;
import com.eduardo.agregadorinvestimentos.entity.Stock;
import com.eduardo.agregadorinvestimentos.repository.AccountRepository;
import com.eduardo.agregadorinvestimentos.repository.AccountStockRepository;
import com.eduardo.agregadorinvestimentos.repository.StockRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    @Value("#{environment.TOKEN}")
    private String TOKEN;
    private final AccountRepository accountRepository;
    private final StockRepository stockRepository;
    private final AccountStockRepository accountStockRepository;
    private final BrapiClient brapiClient;

    public AccountService(AccountRepository accountRepository, StockRepository stockRepository, AccountStockRepository accountStockRepository, BrapiClient brapiClient) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.accountStockRepository = accountStockRepository;
        this.brapiClient = brapiClient;
    }


    public void associateStock(String accountId, AssociateAccountStockDto dto) {
        Account account = accountRepository.findById(UUID.fromString(accountId)).orElseThrow(RuntimeException::new);

        Stock stock = stockRepository.findById(dto.stockId()).orElseThrow(RuntimeException::new);

        AccountStockId id = new AccountStockId(account.getAccountId(), stock.getStockId());
        AccountStock entity = new AccountStock(id, account, stock, dto.quantity());

        accountStockRepository.save(entity);
    }

    public List<AccountStockResponseDto> listStocks(String accountId) {

        Account account = accountRepository.findById(UUID.fromString(accountId)).orElseThrow(RuntimeException::new);

        return account.getAccountStocks()
                .stream()
                .map(as ->
                        new AccountStockResponseDto(
                                as.getStock().getStockId(),
                                as.getQuantity(),
                                getTotal(as.getQuantity(), as.getStock().getStockId())))
                .toList();
    }

    private double getTotal(Integer quantity, String stockId) {
        var response = brapiClient.getQuote(TOKEN, stockId);

        double price = response.results().stream()
                .findFirst()
                .map(result -> result.regularMarketPrice())
                .orElse(0.0);
        return quantity * price;
    }
}
