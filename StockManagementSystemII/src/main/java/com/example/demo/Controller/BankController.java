package com.example.demo.Controller;

import com.example.demo.Entity.BankEntity;
import com.example.demo.Entity.StockEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.Service.BankService;
import com.example.demo.Service.UserAccountService;
import com.example.demo.Util.SessionManagementUtil;
import com.example.demo.Validator.RegisterBankValidator;
import com.example.demo.Validator.RegisterUserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@RestController
public class BankController {

    private static final Logger logger = LoggerFactory.getLogger(BankController.class);

    @Autowired
    private BankService bankService;

    @Autowired
    private SessionManagementUtil sessionManagementUtil;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    RegisterBankValidator registerBankValidator;


    @GetMapping(value = "/registerBank.html")
    public ModelAndView showRegisterPage(HttpServletRequest request, HttpServletResponse response)
    {
        ModelAndView mv = new ModelAndView();

        if (!this.sessionManagementUtil.doesSessionExist(request))
        {
            logger.info("Please login to access this page");
            mv.setViewName("login-form");
            mv.addObject("user", new UserEntity());
            mv.addObject("errorMessage", "Please login to access this page");
            return mv;

        }

        mv.setViewName("register-bank");
        mv.addObject("bankEntity", new BankEntity());
        return mv;
    }

    @PostMapping(value = "/registerBank.html")
    public ModelAndView registerBankAccount(HttpServletRequest request,
                                            HttpServletResponse response, @ModelAttribute("bankEntity") @Validated BankEntity bank, BindingResult bindingResult){
        registerBankValidator.validate(bank,bindingResult);

        String email = (String) request.getSession().getAttribute("user");
        UserEntity user = this.userAccountService.getProfileAttributes(email);

        if (bindingResult.hasErrors())
        {
            ModelAndView mv = new ModelAndView("register-bank");
            mv.addObject("bankEntity", new BankEntity());
            mv.addObject("errorMessage", "Account number must has night digits");
            return mv;
        }

        if(bankService.checkIfBankDuplicated(bank)){
            ModelAndView mv = new ModelAndView("register-bank");
            mv.addObject("bankEntity", new BankEntity());
            mv.addObject("errorMessage", "Oops. A bank account with this number already exists.");
            return mv;
        }
        else if(bankService.registerBank(user,bank))
        {
            logger.info("Bank account successfully registered");
            ModelAndView mv = new ModelAndView("stock-detail");
            mv.addObject("bankEntity", new BankEntity());
            mv.addObject("stockEntity",new StockEntity());
            return mv;
        }
        else {
            ModelAndView mv = new ModelAndView("register-bank");
            mv.addObject("bankEntity", new BankEntity());
            mv.addObject("errorMessage", "Oops. Something went wrong. Please try again.");
            return mv;
        }
    }
}
