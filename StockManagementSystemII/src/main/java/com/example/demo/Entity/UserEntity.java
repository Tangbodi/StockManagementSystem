package com.example.demo.Entity;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Queue;
import java.util.Set;

@Entity
@Table(name = "user")
public class UserEntity{

    public UserEntity(){

    }

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    @Email
    @NotEmpty
    private String email ;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName ;

    @Column(nullable = false)
    private String lastName ;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "userEntity")
    private List<BankEntity> bankEntity;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "userEntity")
    private List<TransactionEntity> transactionEntities;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "userEntity")
    private List<StockHoldingEntity> stockHoldingsEntities;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<BankEntity> getBankEntity() {
        return bankEntity;
    }

    public void setBankEntity(List<BankEntity> bankEntity) {
        this.bankEntity = bankEntity;
    }

    public List<TransactionEntity> getTransactionEntities() {
        return transactionEntities;
    }

    public void setTransactionEntities(List<TransactionEntity> transactionEntities) {
        this.transactionEntities = transactionEntities;
    }

    public List<StockHoldingEntity> getStockHoldingsEntities() {
        return stockHoldingsEntities;
    }

    public void setStockHoldingsEntities(List<StockHoldingEntity> stockHoldingsEntities) {
        this.stockHoldingsEntities = stockHoldingsEntities;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", email='" + getEmail() + "'" +
                ", password='" + getPassword() + "'" +
                ", firstName='" + getFirstName() + "'" +
                ", lastName='" + getLastName() + "'" +
                "}";
    }
}
