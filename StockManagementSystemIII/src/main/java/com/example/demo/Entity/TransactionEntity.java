package com.example.demo.Entity;

import org.apache.catalina.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name ="transaction")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String stockTicker;

    @Column
    private Float dealPrice;

    @Column
    private String tradeType;

    @Column
    private String quantity;

    @Column
    @Temporal(TemporalType.DATE)
    private Date tradeDate;

    @Column
    private Float totalPrice;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name="bank_id", nullable=false)
    private BankEntity bankEntity;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    private UserEntity userEntity;

    public TransactionEntity() {

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStockTicker() {
        return stockTicker;
    }

    public void setStockTicker(String stockTicker) {
        this.stockTicker = stockTicker;
    }

    public Float getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(Float dealPrice) {
        this.dealPrice = dealPrice;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BankEntity getBankEntity() {
        return bankEntity;
    }

    public void setBankEntity(BankEntity bankEntity) {
        this.bankEntity = bankEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                " stockTicker='" + getStockTicker() + "'" +
                " dealPrice='" + getDealPrice() + "'" +
                " tradeType='" + getTradeType() + "'" +
                " quantity='" + getQuantity() + "'" +
                ", tradeDate='" + getTradeDate()+ "'" +
                ", totalPrice='" + getTotalPrice() + "'" +
                "}";
    }
}
