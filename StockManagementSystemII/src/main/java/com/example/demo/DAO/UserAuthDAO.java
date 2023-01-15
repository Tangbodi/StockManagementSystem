package com.example.demo.DAO;
import com.example.demo.Entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Component
public class UserAuthDAO {
    private static final Logger logger = LoggerFactory.getLogger(UserAuthDAO.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public UserEntity saveUser(UserEntity user)
    {
        logger.info("Saving user object to database");
        return this.entityManager.merge(user);
    }

    public int checkIfUserExists(String email)
    {
        logger.info("Checking if user exists");
        int result = 0;
        try {
            Query query = this.entityManager.createQuery("SELECT COUNT(u) FROM UserEntity u WHERE u.email = ?1");
            query.setParameter(1, email);
            query.setMaxResults(1);
            Long resultInLong = (Long) query.getSingleResult();
            result = Math.toIntExact(resultInLong);
        } catch (Exception e) {
            logger.error(e.toString());
            result = 0;
        }

        return result;
    }

    public UserEntity getProfileAttributes(String email) {
        logger.info("Getting user from email : " + email);
        TypedQuery<UserEntity> query = this.entityManager.createQuery("SELECT u FROM UserEntity u WHERE u.email = ?1", UserEntity.class);
        query.setParameter(1, email);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
