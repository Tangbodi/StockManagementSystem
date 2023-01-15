package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

@Entity
@Table(name="bank")
public class BankEntity {

    public BankEntity(){

    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String accountNumber;

    @Column
    private Float totalBalance = 0.0f;

//    @OneToOne(fetch = FetchType.EAGER)
//    @PrimaryKeyJoinColumn
//    private UserEntity userEntity;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", nullable=false)
    private UserEntity userEntity;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "bankEntity")
    private List<TransactionEntity> transactionEntities;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "bankEntity")
    private List<BankStatementEntity> bankStatementEntities;



//    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER, mappedBy = "bankEntity")
//    private List<InventoryEntity> inventoryEntities;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Float getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(Float totalBalance) {
        this.totalBalance = totalBalance;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public List<TransactionEntity> getTransactionEntities() {
        return transactionEntities;
    }

    public void setTransactionEntities(List<TransactionEntity> transactionEntities) {
        this.transactionEntities = transactionEntities;
    }

    public List<BankStatementEntity> getBankStatementEntities() {
        return bankStatementEntities;
    }

    public void setBankStatementEntities(List<BankStatementEntity> bankStatementEntities) {
        this.bankStatementEntities = bankStatementEntities;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                " id='" + getId() + "'" +
                ", accountNumber='" + getAccountNumber() + "'" +
                ", totalBalance='" + getTotalBalance() + "'" +
                //", bankTransaction='" + getBankTransactions() + "'" +
                "}";
    }
}
