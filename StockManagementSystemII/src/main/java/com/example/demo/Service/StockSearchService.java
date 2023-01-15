package com.example.demo.Service;

import com.example.demo.DTO.StockDTO;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;



@Service
public class StockSearchService{

    private static final Logger logger = LoggerFactory.getLogger(StockSearchService.class);
    private String token = "ce348uqad3ici68dq88gce348uqad3ici68dq890";

      public StockDTO getStockInfo(String stockSymbol)  throws IOException {
        URL url = new URL(com.example.demo.Enumeration.Endpoint.QUOTE.url() + "?symbol=" + stockSymbol + "&token=" + token);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
        StringBuilder jsonString = new StringBuilder();
        String readAPIResponse = " ";
        while((readAPIResponse = reader.readLine()) != null){
            jsonString.append(readAPIResponse);
        }
          JSONObject jsonObject = new JSONObject(jsonString.toString());
        String timestampString = String.valueOf(jsonObject.get("t"));
        Long timestampLong = Long.parseLong(timestampString);
        Date date = new Date();
        date.setTime((long)timestampLong*1000);

       return new StockDTO(stockSymbol, jsonObject.get("c"),jsonObject.get("d"),jsonObject.get("dp"),jsonObject.get("h"),jsonObject.get("l"),jsonObject.get("o"),jsonObject.get("pc"),date);
    }
}
