package com.kirillpolyakov.service;

import com.kirillpolyakov.model.SimpleUser;
import com.kirillpolyakov.repository.SimpleUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleUserServiceImpl implements SimpleUserService{

    private SimpleUserRepository simpleUserRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setSimpleUserRepository(SimpleUserRepository simpleUserRepository) {
        this.simpleUserRepository = simpleUserRepository;
    }

    @Override
    public SimpleUser add(SimpleUser simpleUser) {
        try {
            simpleUser.setPassword(passwordEncoder.encode(simpleUser.getPassword()));
            this.simpleUserRepository.save(simpleUser);
            return simpleUser;
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("User is already exist");
        }
    }

    @Override
    public SimpleUser get(long id) {
        return this.simpleUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("SimpleUser doesn't exist"));
    }

    @Override
    public List<SimpleUser> getAll() {
        return this.simpleUserRepository.findAll();
    }
}
