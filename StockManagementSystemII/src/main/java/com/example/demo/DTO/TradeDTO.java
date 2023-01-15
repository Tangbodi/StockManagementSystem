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
public class TradeDTO {
    private String stockTicker;

    private Float dealPrice;

    private String tradeType;

    private Integer quantity;

    private Date tradeDate;

    private Float totalPrice;

    @java.lang.Override
    public java.lang.String toString() {
        return "TradeDTO{" +
                " stockSymbol='" + getStockTicker() + "'" +
                " individualPrice='" + getDealPrice() + "'" +
                ", tradeType='" + getTradeType()+ "'" +
                ", quantity='" + getQuantity() + "'" +
                ", date='" + getTradeDate() + "'" +
                ", totalPrice='" + getTotalPrice() + "'" +
                "}";
    }
}
