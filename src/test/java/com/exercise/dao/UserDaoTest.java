package com.exercise.dao;

import com.exercise.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.swing.*;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    @Autowired
    ApplicationContext context;



    UserDao userDao = new UserDao();
    User user1 = new User("20", "mark20", "twenty");
    User user2 = new User("21", "mark21", "twentyone");
    User user3 = new User("22", "mark22", "twentytwo");

    @Test
    @DisplayName("insert and select test passed.")
    void addAndSelect() throws SQLException, ClassNotFoundException {
        user1 = new User("1", "sangjoon", "1234");

        userDao.deleteAll();
        assertEquals(0, userDao.getCount());

        userDao.add(user1);
        assertEquals(1, userDao.getCount());

        User user = userDao.findById(user1.getId());
        assertEquals(user1.getName(), user.getName());
        assertEquals(user1.getPassword(), user.getPassword());
    }

    @Test
    @DisplayName("count test passed.")
    void count() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();
        assertEquals(0, userDao.getCount());

        userDao.add(user1);
        assertEquals(1, userDao.getCount());
        userDao.add(user2);
        assertEquals(2, userDao.getCount());
        userDao.add(user3);
        assertEquals(3, userDao.getCount());
    }

    @Test
    void findById() throws SQLException, ClassNotFoundException{
        assertThrows(EmptyResultDataAccessException.class, () -> {
            userDao.findById("1000");
        });
    }

}