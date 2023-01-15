package com.example.demo.Service;

import com.example.demo.DTO.TradeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TradeService {
    private static final Logger logger = LoggerFactory.getLogger(TradeService.class);

    public TradeDTO completeTrade(String symbol, String currentPrice, String quantities, String tradeType){

        TradeDTO tradeDTO = new TradeDTO();
        float result =0;
        Integer quantity = Integer.parseInt(quantities);
        Float individualPrice = Float.parseFloat(currentPrice);
        result +=  (quantity)*individualPrice;
        String tradeType1 = tradeType;

        tradeDTO.setStockTicker(symbol);
        tradeDTO.setDealPrice(individualPrice);
        tradeDTO.setTradeType(tradeType1);
        tradeDTO.setQuantity(quantity);
        tradeDTO.setTradeDate(new Date());
        tradeDTO.setTotalPrice(result);
        return tradeDTO;
    }

}
