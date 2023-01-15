package com.example.demo.Entity;

import javax.persistence.*;

@Entity
//@Table(name = "stock")
public class StockEntity {
    public StockEntity(){

    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String stockTicker;



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

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", symbol='" + getStockTicker() + "'" +
                "}";
    }
}
