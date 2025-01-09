package com.kirillpolyakov.repository;

import com.kirillpolyakov.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByQuizResultSimpleUserId(long id);
}
