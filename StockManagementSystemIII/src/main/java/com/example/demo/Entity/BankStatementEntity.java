package com.example.demo.Entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="bankstatement")
public class BankStatementEntity {

    public BankStatementEntity(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String ticker;

    @Column
    private String tradeType;

    @Column
    private Float dealPrice;

    @Column
    private Float newBalance = 0.0f;

    @Column
    private Date tradeDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="bank_id", nullable=false)
    private BankEntity bankEntity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public Float getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(Float dealPrice) {
        this.dealPrice = dealPrice;
    }


    public Float getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(Float newBalance) {
        this.newBalance = newBalance;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public BankEntity getBankEntity() {
        return bankEntity;
    }

    public void setBankEntity(BankEntity bankEntity) {
        this.bankEntity = bankEntity;
    }



    @Override
    public String toString() {
        return "Transactions{" +
                " id='" + getId() + "'" +
                ", ticker='" + getTicker() + "'" +
                ", tradeType='" + getTradeType() + "'" +
                ", dealPrice='" + getDealPrice() + "'" +
                ", totalBalance='" + getNewBalance() + "'" +
                ", date='" + getTradeDate() + "'" +
                ", bankEntity='" + getBankEntity() + "'" +
                "}";
    }
}
