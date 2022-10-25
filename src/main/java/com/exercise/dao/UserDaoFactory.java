package com.exercise.dao;

import org.springframework.context.annotation.Configuration;

@Configuration
public class UserDaoFactory {

    public UserDao awsUserDao(){
        AWSConnectionMaker awsConnectionMaker = new AWSConnectionMaker();
        UserDao userDao = new UserDao(awsConnectionMaker);
        return userDao;
    }
}
