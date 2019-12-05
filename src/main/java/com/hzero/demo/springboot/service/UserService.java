package com.hzero.demo.springboot.service;

import com.hzero.demo.springboot.dao.UserRepository;
import com.hzero.demo.springboot.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void insertTwo() {
        User userA = new User();
        userA.setUsername("A");
        userA.setAge(10);

        User userB = new User();
        userB.setUsername("B");
        userB.setAge(20);

        userRepository.save(userB);
    }


}
