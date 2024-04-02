package com.eduardo.agregadorinvestimentos.service;

import com.eduardo.agregadorinvestimentos.client.BrapiClient;
import com.eduardo.agregadorinvestimentos.dto.AssociateAccountStockDto;
import com.eduardo.agregadorinvestimentos.entity.Account;
import com.eduardo.agregadorinvestimentos.entity.AccountStock;
import com.eduardo.agregadorinvestimentos.entity.AccountStockId;
import com.eduardo.agregadorinvestimentos.entity.Stock;
import com.eduardo.agregadorinvestimentos.repository.AccountRepository;
import com.eduardo.agregadorinvestimentos.repository.AccountStockRepository;
import com.eduardo.agregadorinvestimentos.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private AccountStockRepository accountStockRepository;

    @Mock
    BrapiClient brapiClient;

    @InjectMocks
    AccountService service;


    @Test
    public void testAssociateStock() {
        String accountId = UUID.randomUUID().toString();
        String stockId = "XYZ";
        int quantity = 10;

        Account account = new Account(UUID.fromString(accountId), null, null, "Description", new ArrayList<>());
        Stock stock = new Stock(stockId, "Stock Description");

        when(accountRepository.findById(UUID.fromString(accountId))).thenReturn(Optional.of(account));
        when(stockRepository.findById(stockId)).thenReturn(Optional.of(stock));

        service.associateStock(accountId, new AssociateAccountStockDto(stockId, quantity));

        verify(accountRepository, times(1)).findById(UUID.fromString(accountId));
        verify(stockRepository, times(1)).findById(stockId);
        verify(accountStockRepository, times(1)).save(any(AccountStock.class));
    }

}
