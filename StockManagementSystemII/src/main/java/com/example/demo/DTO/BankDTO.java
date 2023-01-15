package com.example.demo.DTO;

import com.example.demo.Entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankDTO {

    private String accountNumber;

    private Float totalBalance;

    @java.lang.Override
    public java.lang.String toString() {
        return "BankDTO{" +
                " accountNumber='" + getAccountNumber() + "'" +
                " totalBalance='" + getTotalBalance() + "'" +
                "}";
    }
}
