package com.example.demo.Service;

import com.example.demo.DAO.UserAccountDAO;
import com.example.demo.Entity.BankEntity;
import com.example.demo.Entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAccountService
{
    @Autowired
    private UserAccountDAO userAccountDAO;

    public UserEntity getProfileAttributes (String email)
    {
        return this.userAccountDAO.getProfileAttributes(email);
    }

    public UserEntity updateProfile (String  email)
    {
        return this.userAccountDAO.updateProfileAttributes(email);
    }

    public UserEntity updateBankDetails (UserEntity user, BankEntity bankEntity)
    {
        return this.userAccountDAO.updateBankDetails(user , bankEntity);
    }
}
