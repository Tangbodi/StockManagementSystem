package com.example.demo.Service;

import com.example.demo.DAO.StockHoldingDAO;
import com.example.demo.DTO.TradeDTO;
import com.example.demo.Entity.BankEntity;
import com.example.demo.Entity.StockHoldingEntity;
import com.example.demo.Entity.TransactionEntity;
import com.example.demo.Entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockHoldingService {
    @Autowired
    private StockHoldingDAO stockHoldingDAO;

    private static final Logger logger = LoggerFactory.getLogger(StockHoldingService.class);

    public boolean checkStockHolding(String symbol, String quantities, UserEntity user){
        logger.info("Checking stock holding ::" + user.getEmail() + "::"+ symbol);
        Integer quantity = Integer.parseInt(quantities);
        int result = stockHoldingDAO.checkStockHolding(symbol,quantity,user.getId());
        if(result >= quantity){
            return true;
        }
        return false;
    }
    public boolean createStockHolding(TradeDTO tradeDTO, UserEntity user){

        StockHoldingEntity stockHoldingEntity = new StockHoldingEntity();
        Integer quantity = tradeDTO.getQuantity();
        if(tradeDTO.getTradeType().equals("sell")){
            quantity = tradeDTO.getQuantity() * -1;
        }
        stockHoldingEntity.setQuantity(quantity);
        stockHoldingEntity.setTicker(tradeDTO.getStockTicker());
        stockHoldingEntity.setUserEntity(user);

        this.stockHoldingDAO.saveStockHolding(stockHoldingEntity);
        return true;
    }
}
