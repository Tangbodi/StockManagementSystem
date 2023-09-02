package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class StockDTO {
    private String stock_ticker;

    private Object current_price;

    private Object  change;

    private Object  percent_change;

    private Object high_price_of_the_day;

    private Object low_price_of_the_day;

    private Object open_price_of_the_day;

    private Object  previous_close_price;

    private Date timestamp;



    @java.lang.Override
    public java.lang.String toString() {
        return "StockDTO{" +
                " symbol='" + getStock_ticker() + "'" +
                " c='" + getCurrent_price() + "'" +
                ", d='" + getChange() + "'" +
                ", dp='" + getPercent_change() + "'" +
                ", h='" + getHigh_price_of_the_day() + "'" +
                ", l='" + getLow_price_of_the_day() + "'" +
                ", o='" + getOpen_price_of_the_day() + "'" +
                ", pc='" + getPrevious_close_price() + "'" +
                ", t='" + getTimestamp() + "'" +
                "}";
    }
}
