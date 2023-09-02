package com.example.demo.DAO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.example.demo.Entity.BankEntity;
import com.example.demo.Entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserAccountDAO {

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger logger = LoggerFactory.getLogger(UserAccountDAO.class);

    public UserEntity getProfileAttributes(String email) {
        logger.info("Getting user from email : " + email);
        TypedQuery<UserEntity> query = this.entityManager.createQuery("SELECT u FROM UserEntity u WHERE u.email = ?1", UserEntity.class);
        query.setParameter(1, email);
        return query.getSingleResult();
    }

    @Transactional
    public UserEntity updateProfileAttributes(String email) {

        UserEntity getUserFromEmail = getProfileAttributes(email);
        logger.info("updated id::"+getUserFromEmail.getId());
        getUserFromEmail = this.entityManager.find(UserEntity.class, getUserFromEmail.getId());

        flushAndClear();
        return getUserFromEmail;

    }

    @Transactional
    public UserEntity updateBankDetails(UserEntity user, BankEntity bankEntity)
    {
        logger.info("updateBankDetails::"+bankEntity.getAccountNumber());
        bankEntity = this.saveUserBank(bankEntity);
        List<BankEntity> bankList = new ArrayList<>();
        user.setBankEntity(bankList);
//        bankEntity = this.saveUserBank(bankEntity);
//        user.setBankEntity(bankEntity);
        flushAndClear();
        return user;

    }

    @Transactional
    public BankEntity saveUserBank(BankEntity userBank)
    {
        logger.info("Saving Bank object to database");
        return this.entityManager.merge(userBank);
    }

    private void flushAndClear() {
        this.entityManager.flush();
        this.entityManager.clear();
    }

}

