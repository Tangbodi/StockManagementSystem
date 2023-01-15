package com.example.demo.DAO;

import com.example.demo.Entity.BankEntity;
import com.example.demo.Entity.BankStatementEntity;
import com.example.demo.Entity.TransactionEntity;
import com.example.demo.Entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class BankStatementDAO {
    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger logger = LoggerFactory.getLogger(BankStatementDAO.class);

    @Transactional
    public BankStatementEntity saveBankStatement(BankStatementEntity bankStatementEntity)
    {
        logger.info("Saving BankStatement object to database");
        return this.entityManager.merge(bankStatementEntity);
    }
    public List<BankStatementEntity> findAll(UserEntity user){
        logger.info("Retrieving data of bank statement from database::" + user.getId());
        BankEntity bankId = new BankEntity();
        bankId.setId(user.getId());
        TypedQuery typedQuery = this.entityManager.createQuery("SELECT c FROM BankStatementEntity c WHERE c.bankEntity= ?1", BankStatementEntity.class);
        typedQuery.setParameter(1, bankId);
        return typedQuery.getResultList();
    }
    private void flushAndClear() {
        this.entityManager.flush();
        this.entityManager.clear();
    }
}
