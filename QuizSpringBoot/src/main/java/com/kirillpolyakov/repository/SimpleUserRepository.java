package com.kirillpolyakov.repository;

import com.kirillpolyakov.model.SimpleUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleUserRepository extends JpaRepository<SimpleUser, Long> {
}
