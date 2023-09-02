package com.example.demo.DAO;

import com.example.demo.Entity.BankEntity;
import com.example.demo.Entity.BankStatementEntity;
import com.example.demo.Entity.TransactionEntity;
import com.example.demo.Entity.UserEntity;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Component
public class TransactionDAO {
    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger logger = LoggerFactory.getLogger(TransactionDAO.class);

    @Transactional
    public TransactionEntity saveTransaction(TransactionEntity transactionEntity)
    {
        logger.info("Saving Transaction object to database");
        return this.entityManager.merge(transactionEntity);
    }
    public List<TransactionEntity> findAll(UserEntity user){
        logger.info("Retrieving data of transaction from database::"+user.getId());
        UserEntity userId = new UserEntity();
        userId.setId(user.getId());
        TypedQuery typedQuery = this.entityManager.createQuery("SELECT c FROM TransactionEntity c WHERE c.userEntity= ?1",TransactionEntity.class);
        typedQuery.setParameter(1, userId);
        return typedQuery.getResultList();
    }
    private void flushAndClear() {
        this.entityManager.flush();
        this.entityManager.clear();
    }
}
