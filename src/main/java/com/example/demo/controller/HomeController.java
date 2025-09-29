package com.example.demo.controller;

import com.example.demo.enums.Hero;
import com.example.demo.repositotry.SkinRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final SkinRepository skinRepository;

    public HomeController(SkinRepository skinRepository) {
        this.skinRepository = skinRepository;
    }

    @GetMapping("/")
    public String showHeroes(Model model) {
        Hero[] heroes = Hero.values();
        model.addAttribute("heroes", heroes);
        return "index";
    }
}
