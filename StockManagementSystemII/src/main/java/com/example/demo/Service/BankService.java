package com.example.demo.Service;

import com.example.demo.DAO.BankDAO;
import com.example.demo.DTO.BankDTO;
import com.example.demo.DTO.TradeDTO;
import com.example.demo.Entity.BankEntity;
import com.example.demo.Entity.TransactionEntity;
import com.example.demo.Entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

@Service
public class BankService {
    private static final Logger logger = LoggerFactory.getLogger(BankService.class);

    @Autowired
    private BankDAO bankDAO;

    public boolean checkIfBankCreated(UserEntity user){
        String userId = String.valueOf(user.getId());
        logger.info("Checking if user has bank ::" + userId);
        int result = this.bankDAO.checkIfRecordExist(user);
        if (result >0) {
            return true;
        }
        else
            return false;
    }
    public boolean checkIfBankDuplicated(BankEntity bankEntity){
        logger.info("Checking if bank exists::" + bankEntity.getAccountNumber());
        int result = this.bankDAO.checkIfSameBankExist(bankEntity.getAccountNumber());
        if (result >0) {
            return true;
        }
        else
            return false;
    }

    public boolean registerBank(UserEntity user,BankEntity bank){
        bank.setUserEntity(user);
        this.bankDAO.addBankToUser(bank);
        return true;
    }

    public BankEntity getBankDetails(Long bankEntityId){

        return this.bankDAO.getBankInfo(bankEntityId);
    }
    public BankEntity updateBankBalance(Float cost, String tradeType, BankEntity bankEntity){

        return this.bankDAO.updateBankBalance(cost,bankEntity,tradeType);
    }

    public BankDTO completeBankInfo(BankEntity bank){

        BankDTO bankDTO = new BankDTO();

        bankDTO.setAccountNumber(bank.getAccountNumber());
        bankDTO.setTotalBalance(bank.getTotalBalance());

        return bankDTO;
    }
}

