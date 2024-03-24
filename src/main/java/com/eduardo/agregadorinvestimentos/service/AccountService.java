package com.eduardo.agregadorinvestimentos.service;

import com.eduardo.agregadorinvestimentos.dto.AssociateAccountStockDto;
import com.eduardo.agregadorinvestimentos.entity.Account;
import com.eduardo.agregadorinvestimentos.entity.AccountStock;
import com.eduardo.agregadorinvestimentos.entity.AccountStockId;
import com.eduardo.agregadorinvestimentos.entity.Stock;
import com.eduardo.agregadorinvestimentos.repository.AccountRepository;
import com.eduardo.agregadorinvestimentos.repository.AccountStockRepository;
import com.eduardo.agregadorinvestimentos.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final StockRepository stockRepository;
    private final AccountStockRepository accountStockRepository;

    public AccountService(AccountRepository accountRepository, StockRepository stockRepository, AccountStockRepository accountStockRepository) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.accountStockRepository = accountStockRepository;
    }


    public void associateStock(String accountId, AssociateAccountStockDto dto) {
        Account account = accountRepository.findById(UUID.fromString(accountId)).orElseThrow(RuntimeException::new);

        Stock stock = stockRepository.findById(dto.stockId()).orElseThrow(RuntimeException::new);

        AccountStockId id = new AccountStockId(account.getAccountId(), stock.getStockId());
        AccountStock entity = new AccountStock(id,account,stock,dto.quantity());

        accountStockRepository.save(entity);
    }
}
