package com.example.demo.repositotry;

import com.example.demo.entity.Skin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SkinRepository extends JpaRepository<Skin,Long> {
    List<Skin> findByHeroName(String heroName);
}