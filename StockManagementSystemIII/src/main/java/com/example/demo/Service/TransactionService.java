package com.example.demo.Service;

import com.example.demo.DAO.BankDAO;
import com.example.demo.DAO.TransactionDAO;
import com.example.demo.DTO.TradeDTO;
import com.example.demo.Entity.BankEntity;
import com.example.demo.Entity.BankStatementEntity;
import com.example.demo.Entity.TransactionEntity;
import com.example.demo.Entity.UserEntity;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionDAO transactionDAO;
    @Autowired
    private BankDAO bankDAO;
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    public boolean createTransactions(TradeDTO tradeDTO, BankEntity bank, UserEntity user){
        TransactionEntity transaction = new TransactionEntity();
        transaction.setStockTicker(tradeDTO.getStockTicker());
        transaction.setDealPrice(tradeDTO.getDealPrice());
        transaction.setTradeType(tradeDTO.getTradeType());
        transaction.setQuantity(tradeDTO.getQuantity().toString());
        transaction.setTradeDate(tradeDTO.getTradeDate());
        transaction.setTotalPrice(tradeDTO.getTotalPrice());
        transaction.setBankEntity(bank);
        transaction.setUserEntity(user);
        this.transactionDAO.saveTransaction(transaction);
        return true;
    }
    public List<TransactionEntity> getTransactionList(UserEntity user){
        Object obj = this.transactionDAO.findAll(user);
        List<TransactionEntity> list = new ArrayList<>();
        if(obj instanceof ArrayList<?>){
            for(Object o: (List<?>)obj){
                list.add(TransactionEntity.class.cast(o));
            }
        }
        return this.transactionDAO.findAll(user);
    }

}


