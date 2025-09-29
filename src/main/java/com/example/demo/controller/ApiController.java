package com.example.demo.controller;

import com.example.demo.entity.Basket;
import com.example.demo.entity.Skin;
import com.example.demo.entity.User;
import com.example.demo.repositotry.BasketRepository;
import com.example.demo.repositotry.SkinRepository;
import com.example.demo.repositotry.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final UserRepository userRepository;
    private final SkinRepository skinRepository;
    private final BasketRepository basketRepository;

    public ApiController(UserRepository userRepository, SkinRepository skinRepository, BasketRepository basketRepository) {
        this.userRepository = userRepository;
        this.skinRepository = skinRepository;
        this.basketRepository = basketRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    private User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName()).orElseThrow();
    }

    @GetMapping("/skins/{hero}")
    public List<Skin> getSkinsForHero(@PathVariable String hero) {
        return skinRepository.findByHeroName(hero);
    }

//    @PostMapping("/basket/addToBasket")
//    public ResponseEntity<String> addToBasket(@ModelAttribute Basket basket) {
//        basket.setUser(getCurrentUser());
//        basketRepository.save(basket);
//        return ResponseEntity.ok("ok");
//    }

    @GetMapping("/basket/addToBasket/{skinId}")
    public ResponseEntity<String> addToBasket(@PathVariable Long skinId) {
        User currentUser = getCurrentUser();
        Basket basketItem = new Basket();
        basketItem.setSkinId(skinId);
        basketItem.setUser(currentUser);
        basketRepository.save(basketItem);
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/basket")
    public List<Skin> getBasket() {
        User currentUser = getCurrentUser();
        List<Basket> baskets = basketRepository.findByUser(currentUser);
        if (!baskets.isEmpty()) {
            List<Long> skinIds = new ArrayList<>();
            for (Basket basket : baskets) {
                skinIds.add(basket.getSkinId());
            }
            return skinRepository.findAllById(skinIds);
        }
        return Collections.emptyList();
    }
}