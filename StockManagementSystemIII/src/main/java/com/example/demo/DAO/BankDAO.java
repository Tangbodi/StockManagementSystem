package com.example.demo.DAO;

import com.example.demo.Entity.BankEntity;
import com.example.demo.Entity.TransactionEntity;
import com.example.demo.Entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
public class BankDAO {

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger logger = LoggerFactory.getLogger(BankDAO.class);

    public UserEntity getProfileAttributes(String email) {
        logger.info("Getting user from email : " + email);
        TypedQuery<UserEntity> query = this.entityManager.createQuery("SELECT accountNum"+","+"totalBalance" + "FROM UserEntity u WHERE u.email = ?1", UserEntity.class);
        query.setParameter(1, email);
        return query.getSingleResult();
    }

//    public UserEntity addBankToUser(UserEntity userEntity, BankEntity bank)
//    {
//        logger.info("updateBankInfo::"+bank.getAccountNumber());
//        bank = this.saveUserBank(bank);
//        List<BankEntity> bankList = new ArrayList<>();
//        bankList.add(bank);
//        userEntity.setBankEntity(bankList);
//
//        flushAndClear();
//        return userEntity;
//    }
@Transactional
public BankEntity addBankToUser(BankEntity bank)
    {
        logger.info("Saving bank object to database");
        return this.entityManager.merge(bank);
    }
    public int checkIfRecordExist(UserEntity user){
        String userId = String.valueOf(user);
        logger.info("Checking if user has bank ::" + userId);
        int result = 0;
        try {
            Query query = this.entityManager.createQuery("SELECT COUNT(u) FROM BankEntity u WHERE u.userEntity= ?1");
            query.setParameter(1, user);
            query.setMaxResults(1);
            Long resultInLong = (Long) query.getSingleResult();
            result = Math.toIntExact(resultInLong);
        } catch (Exception e) {
            logger.error(e.toString());
            result = 0;
        }
        return result;
    }
    public int checkIfSameBankExist(String accountNumber){
        logger.info("Checking if bank exists::"+ accountNumber);
        int result = 0;
        try {
            Query query = this.entityManager.createQuery("SELECT COUNT(u) FROM BankEntity u WHERE u.accountNumber = ?1");
            query.setParameter(1, accountNumber);
            query.setMaxResults(1);
            Long resultInLong = (Long) query.getSingleResult();
            result = Math.toIntExact(resultInLong);
        } catch (Exception e) {
            logger.error(e.toString());
            result = 0;
        }
        return result;
    }

    public BankEntity getBankInfo(Long bankEntityId){
        logger.info("Retrieving details of bank" + bankEntityId);
        TypedQuery<BankEntity> query = this.entityManager.createQuery("SELECT u FROM BankEntity u WHERE u.id=:id", BankEntity.class);
        query.setParameter("id",bankEntityId);
        return query.getSingleResult();
    }

    @Transactional
    public BankEntity saveUserBank(BankEntity bankEntity)
    {
        logger.info("Saving bank object to database");
        return this.entityManager.merge(bankEntity);
    }

    @Transactional
    public BankEntity updateBankBalance(Float cost,BankEntity bank,String tradeType){
        logger.info("Updating balance of bank::"+bank.getAccountNumber());
        Float newBalance = bank.getTotalBalance();
        if(tradeType.equals("buy")||(tradeType.equals("withdraw"))){
            newBalance =  newBalance-cost;
        }
        else if(tradeType.equals("sell") || (tradeType.equals("deposit"))){
            newBalance = newBalance+cost;
        }
        bank.setTotalBalance(newBalance);
       return this.entityManager.merge(bank);
    }


    private void flushAndClear() {
        this.entityManager.flush();
        this.entityManager.clear();
    }

}
