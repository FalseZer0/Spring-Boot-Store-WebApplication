package com.geekbrains.demoboot.repositories;

import com.geekbrains.demoboot.entities.Cat;
import com.geekbrains.demoboot.entities.Product;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class CatRepository {
    private List<Cat> cats;

    @PostConstruct
    public void init() {
        cats = new ArrayList<>();
        cats.add(new Cat(1L,"Cerf","black"));
        cats.add(new Cat(213L,"GGG","block"));
    }

    public List<Cat> findAll() {
        return cats;
    }

    public Cat findByTitle(String title) {
        return cats.stream().filter(p -> p.getName().equals(title)).findFirst().get();
    }

    public Cat findById(Long id) {
        return cats.stream().filter(p -> p.getId().equals(id)).findFirst().get();
    }

    public void save(Cat cat) {
        cats.add(cat);
    }
}