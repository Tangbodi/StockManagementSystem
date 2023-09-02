package com.example.demo.Controller;

import com.example.demo.DTO.BankDTO;
import com.example.demo.DTO.TradeDTO;
import com.example.demo.Entity.BankEntity;
import com.example.demo.Entity.BankStatementEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.Service.BankService;
import com.example.demo.Service.BankStatementService;
import com.example.demo.Service.UserAccountService;
import com.example.demo.Util.SessionManagementUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class ManageBankController {
    private static final Logger logger = LoggerFactory.getLogger(ManageBankController.class);

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    BankService bankService;

    @Autowired
    BankStatementService bankStatementService;

    @Autowired
    private SessionManagementUtil sessionManagementUtil;

    @GetMapping(value = "/manageBank.html")
    public ModelAndView reviewBankDetail(HttpServletRequest request,  Model model)
    {
        if (!this.sessionManagementUtil.doesSessionExist(request))
        {
            logger.info("Please login to access this page");
            ModelAndView mv = new ModelAndView("login-form");
            mv.addObject("user", new UserEntity());
            mv.addObject("errorMessage", "Please login to access this page");
            return mv;

        }

        String email = (String) request.getSession().getAttribute("user");
        UserEntity user = userAccountService.getProfileAttributes(email);
        Long bankEntityId = user.getBankEntity().get(0).getId();
        BankEntity bank  = bankService.getBankDetails(bankEntityId);
        BankDTO bankDTO = bankService.completeBankInfo(bank);
        List<BankDTO>bankInfoList = new ArrayList<>();
        bankInfoList.add(bankDTO);

        request.getSession().setAttribute("bank", bank);

        ModelAndView mv = new ModelAndView();
        model.addAttribute("bankInfoList",bankInfoList);
        mv.setViewName("manage-bank");
        return mv;
    }

    @PostMapping(value = "/manageBank.html")
    public ModelAndView manageBankDetail(HttpServletRequest request, Model model){

        String email = (String) request.getSession().getAttribute("user");
        UserEntity user = userAccountService.getProfileAttributes(email);
        Long bankEntityId = user.getBankEntity().get(0).getId();
        BankEntity bank  = bankService.getBankDetails(bankEntityId);

        String operationType = request.getParameter("operationType");
        String inputAmount = request.getParameter("inputAmount");
        Float amount = Float.parseFloat(inputAmount);
        System.out.println(amount+""+operationType);

        TradeDTO tradeDTO = new TradeDTO();
        tradeDTO.setTradeType(operationType);
        tradeDTO.setTotalPrice(amount);
        tradeDTO.setTradeDate(new Date());

        ModelAndView mv = new ModelAndView();

        if(bankStatementService.createBankStatement(tradeDTO, bank)){
            logger.info("Bank statement successfully created::");
            mv.addObject("bankStatement", new BankStatementEntity());
        }
        bankService.updateBankBalance(tradeDTO.getTotalPrice(),operationType,bank);
        request.getSession().setAttribute("bank", bank);
        mv.setViewName("manage-bank");
        return new ModelAndView("redirect:/manageBank.html");
    }
}
