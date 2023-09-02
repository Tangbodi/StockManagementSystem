package com.example.demo.Validator;

import com.example.demo.Entity.BankEntity;
import com.example.demo.Entity.UserEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RegisterUserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz)
    {

        return UserEntity.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors)
    {
        UserEntity login = (UserEntity) (target);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.email.required", "Email is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.password.required", "Password is required.");

        if (errors.hasErrors())
        {
            return;
        }

        boolean verifyPass = this.verifyPassword(login.getPassword());
        if (!verifyPass)
        {
            errors.rejectValue("password", "error.password.required", "Password should minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character");
        }

    }

//    public void bankAccountValidate(Object target,Errors errors){
//        BankEntity login = (BankEntity) (target);
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "accountNumber", "error.accountNumber.required","Account Number is required");
//
//        if(errors.hasErrors()){
//            return;
//        }
//        boolean verifyPass = this.verifyPassword(login.getAccountNumber());
//        if(!verifyPass){
//            errors.rejectValue("accountNumber", "error.accountNumber.required", "Account number must has night digits");
//        }
//    }
//    public boolean verifyBankAccountNumber(String accountNumber){
//        boolean isAccount = false;
//
//        Pattern accountPattern = Pattern.compile("^(?=(.*[\\d]){9,}).{9,}$");
//        Matcher m = accountPattern.matcher(accountNumber);
//        isAccount = m.find();
//
//        return isAccount;
//    }
    public boolean verifyPassword(String password)
    {
        boolean isPassword = false;

        //Checks if password contains minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character
        Pattern passwordPattern = Pattern.compile("^(?=.*?[A-Z])(?=(.*[a-z]){1,})(?=(.*[\\d]){1,})(?=(.*[\\W_]){1,})(?!.*\\s).{8,}$");

        Matcher m = passwordPattern.matcher(password);
        isPassword = m.find();

        return isPassword;
    }
}
