package com.eduardo.agregadorinvestimentos.service;

import com.eduardo.agregadorinvestimentos.entity.Stock;
import com.eduardo.agregadorinvestimentos.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StockServiceTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    StockService service;

    @Test
    public void testCreateStock(){
        Stock stock = new Stock("PETR4", "Petrobras");

        when(stockRepository.save(any(Stock.class))).thenReturn(stock);

        Stock createStock = service.createStock(stock);

        assertNotNull(createStock);
        verify(stockRepository, times(1)).save(any(Stock.class));
    }
}
