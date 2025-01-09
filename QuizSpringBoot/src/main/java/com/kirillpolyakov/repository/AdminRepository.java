package com.kirillpolyakov.repository;

import com.kirillpolyakov.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
