package com.example.demo.Controller;

import com.example.demo.DTO.BankDTO;
import com.example.demo.DTO.StockDTO;

import com.example.demo.Entity.BankEntity;
import com.example.demo.Entity.StockEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.Service.BankService;
import com.example.demo.Service.StockSearchService;

import com.example.demo.Service.UserAccountService;
import com.example.demo.Util.SessionManagementUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@RestController
public class StockSearchController {
    private static final Logger logger = LoggerFactory.getLogger(StockSearchController.class);

    @Autowired
    private StockSearchService stockSearchService;

    @Autowired
    private BankService bankService;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private SessionManagementUtil sessionManagementUtil;

    @GetMapping(value = "/stockDetail.html")
    public ModelAndView showStockInfoPage(HttpServletRequest request, HttpServletResponse response,  @ModelAttribute("stockEntity") @Validated StockEntity stockEntity, Model model) throws IOException {

        if (!this.sessionManagementUtil.doesSessionExist(request))
        {
            logger.info("Please login to access this page");
            ModelAndView mv = new ModelAndView("login-form");
            mv.addObject("user", new UserEntity());
            mv.addObject("errorMessage", "Please login to access this page");
            return mv;

        }

        ModelAndView mv = new ModelAndView();
        mv.addObject("stockEntity",stockEntity);

        //--------Stock Details---------
        StockDTO stockDTO = stockSearchService.getStockInfo(stockEntity.getStockTicker());
        List<StockDTO> stockList = new ArrayList<>();
        stockList.add(stockDTO);

        //---------Get Bank Detail----------
        String email = (String) request.getSession().getAttribute("user");
        UserEntity user = userAccountService.getProfileAttributes(email);
        Long bankEntityId = user.getBankEntity().get(0).getId();
        BankEntity bank  = bankService.getBankDetails(bankEntityId);
        BankDTO bankDTO = bankService.completeBankInfo(bank);
        List<BankDTO>bankInfoList = new ArrayList<>();
        bankInfoList.add(bankDTO);

        //------------------------------
        model.addAttribute("bankInfoList",bankInfoList);
        model.addAttribute("stockList", stockList);
        mv.setViewName("stock-detail");
        return mv;
    }

    @PostMapping(value = "/stockDetail.html")
    public ModelAndView enterNewStockSymbol(HttpServletRequest request, HttpServletResponse response,
                                            @ModelAttribute("stockEntity") @Validated StockEntity stockEntity,  Model model) throws IOException, ParseException {

        ModelAndView mv = new ModelAndView();
        mv.addObject("stockEntity",stockEntity);

        //--------Stock Details---------
        StockDTO stockDTO = stockSearchService.getStockInfo(stockEntity.getStockTicker().toUpperCase());
        List<StockDTO> stockList = new ArrayList<>();
        stockList.add(stockDTO);

        //---------Get Bank Detail----------
        String email = (String) request.getSession().getAttribute("user");
        UserEntity user = userAccountService.getProfileAttributes(email);
        Long bankEntityId = user.getBankEntity().get(0).getId();
        BankEntity bank  = bankService.getBankDetails(bankEntityId);
        BankDTO bankDTO = bankService.completeBankInfo(bank);
        List<BankDTO>bankInfoList = new ArrayList<>();
        bankInfoList.add(bankDTO);

        //------------------------------
        model.addAttribute("bankInfoList",bankInfoList);
        model.addAttribute("stockList", stockList);
        mv.setViewName("stock-detail");
        request.getSession().setAttribute("stockList", stockList);
        request.getSession().setAttribute("bank", bank);
        return mv;
    }
}
