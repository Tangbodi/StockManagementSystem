package com.example.demo.DAO;

import com.example.demo.DTO.TradeDTO;
import com.example.demo.Entity.BankStatementEntity;
import com.example.demo.Entity.StockHoldingEntity;
import com.example.demo.Entity.UserEntity;
import com.mysql.cj.jdbc.result.UpdatableResultSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Component
public class StockHoldingDAO {
    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger logger = LoggerFactory.getLogger(StockHoldingDAO.class);

    public int checkStockHolding(String symbol, Integer quantity, Long userId)
    {
        logger.info("Checking if user are holding enough shares::" + symbol);
        int result = 0;
        try {
//            Query query = this.entityManager.createQuery("SELECT SUM(quantity) as share FROM StockHoldingEntity u WHERE u.ticker =?symbol AND u.id=?userId").;
//            query.setParameter("symbol", symbol);
//            query.setParameter("userid", userId);
            Connection connection = DriverManager.getConnection("jdbc:mysql://stockmanagementdb.ctg935aty5id.us-east-1.rds.amazonaws.com:3306/stockdatabase?useSSL=false","root","1992530j");
            String str = "SELECT sum(quantity) as share FROM stockholding WHERE ticker ="+"'"+symbol+"'"+" AND user_id="+"'"+userId+"'";
            PreparedStatement preparedStatement = connection.prepareStatement(str);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String sum = resultSet.getString("share");
                System.out.print("############################" + sum+"\n");
                result = Integer.parseInt(sum);
            }

//            Long resultInLong = (Long) query.getSingleResult();
//            result = Math.toIntExact(resultInLong);
        } catch (Exception e) {
            logger.error(e.toString());
            result = 0;
        }

        return result;
    }
    @Transactional
    public StockHoldingEntity saveStockHolding(StockHoldingEntity stockHoldingEntity)
    {
        logger.info("Saving stock holding object to database");
        return this.entityManager.merge(stockHoldingEntity);
    }
}
