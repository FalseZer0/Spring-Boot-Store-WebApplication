package com.geekbrains.demoboot.services;

import com.geekbrains.demoboot.entities.Cat;
import com.geekbrains.demoboot.entities.Product;
import com.geekbrains.demoboot.repositories.CatRepository;
import com.geekbrains.demoboot.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CatService {
    private CatRepository productRepository;

    @Autowired
    public void setProductRepository(CatRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Cat getById(Long id) {
        return productRepository.findById(id);
    }

    public List<Cat> getAllProducts() {
        return productRepository.findAll();
    }

    public void add(Cat product) {
        productRepository.save(product);
    }
}
