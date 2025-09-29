package com.example.demo.controller;

import com.example.demo.entity.Skin;
import com.example.demo.repositotry.SkinRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SkinController {
    private final SkinRepository skinRepository;

    public SkinController(SkinRepository skinRepository) {
        this.skinRepository = skinRepository;
    }

    @GetMapping("/add")
    public String addSkin(Model model) {
        model.addAttribute("skin", new Skin());
        return "add-skin";
    }

    @PostMapping("/add")
    public String addSkin(@ModelAttribute Skin skin) {
        skinRepository.save(skin);
        return "redirect:/";
    }
}
