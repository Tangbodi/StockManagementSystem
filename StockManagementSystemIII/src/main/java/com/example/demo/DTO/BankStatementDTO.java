package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankStatementDTO {

        private String tradeType;

        private Float dealPrice;

        private Float newBalance;

        private Date tradeDate;


        @java.lang.Override
        public java.lang.String toString() {
            return "BankStatementDTO{" +
                    " tradeType='" + getTradeType() + "'" +
                    " dealPrice='" + getDealPrice() + "'" +
                    " newBalance='" + getNewBalance() + "'" +
                    ", tradeDate='" + getTradeDate() + "'" +
                    "}";
        }
}
