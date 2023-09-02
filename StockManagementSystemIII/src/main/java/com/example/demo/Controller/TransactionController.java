package com.example.demo.Controller;


import com.example.demo.Entity.TransactionEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.Service.TransactionService;
import com.example.demo.Service.UserAccountService;
import com.example.demo.Util.SessionManagementUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class TransactionController {
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    TransactionService transactionService;

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    private SessionManagementUtil sessionManagementUtil;

    @GetMapping(value ="/transaction.html")
    public ModelAndView transactionMapper(HttpServletRequest request, Model model){

        if (!this.sessionManagementUtil.doesSessionExist(request))
        {
            logger.info("Please login to access this page");
            ModelAndView mv = new ModelAndView("login-form");
            mv.addObject("user", new UserEntity());
            mv.addObject("errorMessage", "Please login to access this page");
            return mv;

        }

        String email = (String) request.getSession().getAttribute("user");
        UserEntity user = this.userAccountService.getProfileAttributes(email);
        List<TransactionEntity> transactionList = transactionService.getTransactionList(user);
        model.addAttribute("transactionList",transactionList);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("stock-transaction");
        return mv;
    }

}
