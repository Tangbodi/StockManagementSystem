package com.example.demo.Entity;

import javax.persistence.*;

@Entity
@Table(name = "stockholding")
public class StockHoldingEntity {

    public StockHoldingEntity(){

    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String ticker;

    @Column
    private Integer quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", nullable=false)
    private UserEntity userEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
    @Override
    public String toString() {
        return "StockInventory{" +
                " id='" + getId() + "'" +
                ", ticker='" + getTicker() + "'" +
                ", quantity='" + getQuantity() + "'" +
                "}";
    }
}

