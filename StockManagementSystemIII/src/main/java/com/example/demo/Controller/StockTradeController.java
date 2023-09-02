package com.example.demo.Controller;

import com.example.demo.DTO.StockDTO;
import com.example.demo.DTO.TradeDTO;
import com.example.demo.Entity.*;
import com.example.demo.Service.*;
import com.example.demo.Util.SessionManagementUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class StockTradeController {

    private static final Logger logger = LoggerFactory.getLogger(StockTradeController.class);

    @Autowired
    private TradeService tradeService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private BankService bankService;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private BankStatementService bankStatementService;

    @Autowired
    private StockHoldingService stockHoldingService;

    @Autowired
    private SessionManagementUtil sessionManagementUtil;

//    @Autowired
//    private AsyncService asyncService;

    @GetMapping(value ="/stockDetail/trade.html")
    public ModelAndView completeTransaction(HttpServletRequest request, @ModelAttribute("stockEntity") @Validated StockEntity stockEntity){
        if (!this.sessionManagementUtil.doesSessionExist(request))
        {
            logger.info("Please login to access this page");
            ModelAndView mv = new ModelAndView("login-form");
            mv.addObject("user", new UserEntity());
            mv.addObject("errorMessage", "Please login to access this page");
            return mv;

        }
        logger.info("showTradePage::");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user-trade");
        return mv;
    }
    @PostMapping(value ="/stockDetail/trade.html")
    public ModelAndView setQuantity(HttpServletRequest request,Model model,@ModelAttribute("stockEntity") @Validated StockEntity stockEntity){

        String email = (String) request.getSession().getAttribute("user");
        UserEntity user = userAccountService.getProfileAttributes(email);
        //--------Trade Details---------
        String quantities = request.getParameter("quantity");
        String tradeType = request.getParameter("tradeType");
        List<StockDTO> stockList = (List<StockDTO>) request.getSession().getAttribute("stockList");
        StockDTO stockDTO1 = stockList.get(0);
        String symbol = stockDTO1.getStock_ticker();
        String currentPrice = stockDTO1.getCurrent_price().toString();
        Float cost = Integer.parseInt(quantities) * Float.parseFloat(stockDTO1.getCurrent_price().toString());
        BankEntity bank = (BankEntity) request.getSession().getAttribute("bank");
        //--------Check StockHolding---------
            if(tradeType.equals("sell")){
                ModelAndView mv = new ModelAndView();
                if(!stockHoldingService.checkStockHolding(symbol, quantities,user)){
                    mv.addObject("errorMessage", "The stock holdings of" +" " +symbol + " "+"in your account are not enough");
                    mv.setViewName("stock-detail");
                    return mv;
                }
//                ModelAndView mv = new ModelAndView("register-form");
//                mv.addObject("user", new UserEntity());
//                mv.addObject("errorMessage", "Oops. An account with this email already exists.");
//                return mv;
                else{
                    TradeDTO tradeDTO = tradeService.completeTrade(symbol,currentPrice,quantities,tradeType);
                    List<TradeDTO>tradeList = new ArrayList<>();
                    tradeList.add(tradeDTO);
                    //--------create Transaction---------

                    if(transactionService.createTransactions(tradeDTO,bank,user)){
                        logger.info("Transaction successfully created::");
                        mv.addObject("transaction", new TransactionEntity());
                    }
                    if(bankStatementService.createBankStatement(tradeDTO, bank)){
                        logger.info("Bank statement successfully created::");
//                        asyncService.threading("index = ##########" );
                        mv.addObject("bankStatement", new BankStatementEntity());
                    }
                    if(stockHoldingService.createStockHolding(tradeDTO, user)){
                        logger.info("Stock holding successfully created::");
                        mv.addObject("stockHolding",new StockHoldingEntity());
                    }
                    bankService.updateBankBalance(tradeDTO.getTotalPrice(),tradeType,bank);
                    mv.addObject("tradeList",tradeList);
                    model.addAttribute("tradeList",tradeList);
                    mv.setViewName("user-trade");
                }
                return mv;
            }
            else if(tradeType.equals("buy")){
                ModelAndView mv = new ModelAndView();

                if(bankService.getBankDetails(bank.getId()).getTotalBalance() < cost){
                    mv.addObject("errorMessage", "The available balance on your bank account is not enough to purchase");
                    mv.setViewName("stock-detail");
                    return mv;
                }
                else{
                    TradeDTO tradeDTO = tradeService.completeTrade(symbol,currentPrice,quantities,tradeType);
                    List<TradeDTO>tradeList = new ArrayList<>();
                    tradeList.add(tradeDTO);

                    //--------create Transaction---------

                    if(transactionService.createTransactions(tradeDTO,bank,user)){
                        logger.info("Transaction successfully created::");
                        mv.addObject("transaction", new TransactionEntity());
                    }
                    if(bankStatementService.createBankStatement(tradeDTO, bank)){
                        logger.info("Bank statement successfully created::");
                        mv.addObject("bankStatement", new BankStatementEntity());
                    }
                    if(stockHoldingService.createStockHolding(tradeDTO, user)){
                        logger.info("Stock holding successfully created::");
                        mv.addObject("stockHolding",new StockHoldingEntity());
                    }
                    bankService.updateBankBalance(tradeDTO.getTotalPrice(),tradeType,bank);
                    mv.addObject("tradeList",tradeList);
                    model.addAttribute("tradeList",tradeList);
                    mv.setViewName("user-trade");
                }
                return mv;
            }
        return new ModelAndView("redirect:/stockDetail.html","stockEntity",new StockEntity());
    }
}
