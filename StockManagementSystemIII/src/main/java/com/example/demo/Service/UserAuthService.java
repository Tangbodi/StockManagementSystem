package com.example.demo.Service;

import com.example.demo.DAO.UserAuthDAO;
import com.example.demo.Entity.LoginEntity;
import com.example.demo.Entity.UserEntity;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import java.io.IOException;

@Service
public class UserAuthService {
    private static final Logger logger = LoggerFactory.getLogger(UserAuthService.class);

    @Autowired
    private UserAuthDAO userAuthDAO;

    public boolean registerUser(UserEntity user) throws IllegalStateException
    {
        user.setPassword(BCrypt.hashpw( user.getPassword(), BCrypt.gensalt()));
        this.userAuthDAO.saveUser(user);
        return true;
    }

    public boolean checkIfUserRegistered (UserEntity user)
    {
        logger.info("Checking if user exists ::" + user.getEmail());
        int result = this.userAuthDAO.checkIfUserExists(user.getEmail());
        if (result >0) {
            return true;
        }
        else
            return false;
    }

    public Boolean authenticate(LoginEntity u)
    {
        logger.info("Checking if user exists on the basis of email::" + u.getEmail());

        UserEntity user = null;
        try {
            user = this.userAuthDAO.getProfileAttributes(u.getEmail());
        } catch (Exception e) {
            logger.error(e.toString());
            user = null;
        }

        if (user == null) {
            return false;
        } else {
            Boolean pwdCheck = false;

            if (BCrypt.checkpw(u.getPassword(), user.getPassword())) {
                pwdCheck = true;
            }
            return pwdCheck;
        }

    }

}
