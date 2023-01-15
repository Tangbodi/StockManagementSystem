package com.example.demo.Entity;

import javax.persistence.*;

@Entity
//@Table(name = "trade")
public class TradeEntity {
    public TradeEntity(){

    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String stockTicker;

    @Column
    private String dealPrice;

    @Column
    private String quantity;

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

    public String getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(String dealPrice) {
        this.dealPrice = dealPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", symbol='" + getStockTicker() + "'" +
                ", individualPrice='" + getDealPrice() + "'" +
                ", quantity='" + getQuantity() + "'" +
                "}";
    }
}
