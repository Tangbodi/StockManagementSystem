package com.example.demo.Service;

import com.example.demo.DAO.BankStatementDAO;
import com.example.demo.DTO.TradeDTO;
import com.example.demo.Entity.BankEntity;
import com.example.demo.Entity.BankStatementEntity;
import com.example.demo.Entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Service
public class BankStatementService {
    @Autowired
    private BankStatementDAO bankStatementDAO;

    private static final Logger logger = LoggerFactory.getLogger(BankStatementService.class);

    public boolean createBankStatement(TradeDTO tradeDTO, BankEntity bank){
        BankStatementEntity bankStatement = new BankStatementEntity();

        Float balance = bank.getTotalBalance();
        String tradeType = tradeDTO.getTradeType();
        Float amount = tradeDTO.getTotalPrice();
        if(tradeType.equals("buy")){
            balance =  balance-amount;
        }
        if(tradeType.equals("withdraw") && balance >= amount){
            balance =  balance-amount;
        }
        if(tradeType.equals("deposit")){
            balance = balance+amount;
        }
        else if(tradeType.equals("sell")){
            balance = balance+amount;
        }
        bankStatement.setTicker(tradeDTO.getStockTicker());
        bankStatement.setTradeType(tradeType);
        bankStatement.setDealPrice(amount);
        bankStatement.setNewBalance(balance);
        bankStatement.setTradeDate(tradeDTO.getTradeDate());
        bankStatement.setBankEntity(bank);
        this.bankStatementDAO.saveBankStatement(bankStatement);
        return true;
    }
    public List<BankStatementEntity> getBankStatementList(UserEntity user){
        logger.info("Retrieving bank statement of user ::"+user.getId());
        long start = System.currentTimeMillis();
        Object obj = this.bankStatementDAO.findAll(user);
        List<BankStatementEntity> list = new ArrayList<>();
        if(obj instanceof ArrayList<?>){
            for(Object o: (List<?>)obj){
                list.add(BankStatementEntity.class.cast(o));
            }
        }
        logger.info("Total time {}",(System.currentTimeMillis() - start));
        return this.bankStatementDAO.findAll(user);
    }

}
