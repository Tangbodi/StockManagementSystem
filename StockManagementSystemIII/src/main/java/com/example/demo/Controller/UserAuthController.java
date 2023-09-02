package com.example.demo.Controller;

import com.example.demo.Entity.BankEntity;
import com.example.demo.Entity.LoginEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.Service.BankService;
import com.example.demo.Service.UserAccountService;
import com.example.demo.Service.UserAuthService;
import com.example.demo.Util.SessionManagementUtil;
import com.example.demo.Validator.RegisterUserValidator;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/")
public class UserAuthController {
    private static final Logger logger = LoggerFactory.getLogger(UserAuthController.class);

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private SessionManagementUtil sessionMgmtUtils;

    @Autowired
    RegisterUserValidator registerUserValidator;

    @Autowired
    private BankService bankService;

    @Autowired
    private UserAccountService userAccountService;

    @GetMapping(value = "/register.html")
    public ModelAndView showRegisterPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("register-form");
        mv.addObject("user", new UserEntity());
        return mv;
    }

    @PostMapping(value = "/register.html")
    public ModelAndView registerUser(HttpServletRequest request,
                                     HttpServletResponse response, @ModelAttribute("user") @Validated UserEntity user, BindingResult bindingResult) {

        registerUserValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors())
        {
            ModelAndView mv = new ModelAndView("register-form");
            mv.addObject("user", new UserEntity());
            mv.addObject("errorMessage", "Password should be minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character");
            return mv;
        }

        if (this.userAuthService.checkIfUserRegistered(user)) {
            ModelAndView mv = new ModelAndView("register-form");
            mv.addObject("user", new UserEntity());
            mv.addObject("errorMessage", "Oops. An account with this email already exists.");
            return mv;
        }

        if (userAuthService.registerUser(user))
        {
            logger.info("User successfully registered");
            ModelAndView mv = new ModelAndView("login-form");
            mv.addObject("user", new UserEntity());
            return mv;
        }
        else
        {
            ModelAndView mv = new ModelAndView("register-form");
            mv.addObject("user", new UserEntity());
            mv.addObject("errorMessage", "Oops. Something went wrong. Please try again.");
            return mv;

        }

    }

    @GetMapping(value = "/login.html")
    public ModelAndView showLoginPage(HttpServletRequest request, HttpServletResponse response, LoginEntity user) {
        ModelAndView mv = new ModelAndView("login-form");
        mv.addObject("user", new UserEntity());
        return mv;
    }

    @PostMapping(value = "/login.html")
    public ModelAndView loginUser(HttpServletRequest request, HttpServletResponse response,
                                  @ModelAttribute("user") @Validated LoginEntity loginEntity, BindingResult bindingResult, ModelMap map) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("login-form");
        }

        if (this.userAuthService.authenticate(loginEntity) == true) {
            logger.info("Successfully authenticated");
            this.sessionMgmtUtils.createNewSessionForUser(request, loginEntity.getEmail());
            UserEntity user = userAccountService.getProfileAttributes(loginEntity.getEmail());

            if(this.bankService.checkIfBankCreated(user)){
                return new ModelAndView("redirect:/stockDetail.html");
            }
            else {
                return new ModelAndView("redirect:/registerBank.html");
            }

        }
        else{
            logger.info("Email address or Password is wrong");
        }
            ModelAndView mv = new ModelAndView("login-form");
            return mv;
    }

    @RequestMapping(value = "/logout.html")
    public ModelAndView logoutUser(HttpServletRequest request)
    {
        logger.info("Logging out");
        request.getSession().invalidate();
        return new ModelAndView("redirect:/");
    }
}
