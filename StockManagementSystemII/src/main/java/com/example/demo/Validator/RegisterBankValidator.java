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
public class RegisterBankValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return BankEntity.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BankEntity login = (BankEntity) (target);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "accountNumber", "error.accountNumber.required","Account Number is required");

        if(errors.hasErrors()){
            return;
        }
        boolean verifyAccount = this.verifyBankAccountNumber(login.getAccountNumber());
        if(!verifyAccount){
            errors.rejectValue("accountNumber", "error.accountNumber.required", "Account number must has night digits");
        }
    }
    public boolean verifyBankAccountNumber(String accountNumber){
        boolean isAccount = false;

        Pattern accountPattern = Pattern.compile("^(?=(.*[\\d]){9,}).{9,}$");
        Matcher m = accountPattern.matcher(accountNumber);
        isAccount = m.find();

        return isAccount;
    }
}
