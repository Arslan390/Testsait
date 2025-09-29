package com.example.demo.repositotry;

import com.example.demo.entity.Basket;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    List<Basket> findByUser(User user);
}
