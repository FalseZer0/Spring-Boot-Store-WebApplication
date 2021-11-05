package com.geekbrains.demoboot.repositories.specifications;

import com.geekbrains.demoboot.entities.Product;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import javax.persistence.criteria.Root;

public class ProductSpecs {
    public static Specification<Product> productFiltered(String filter)
    {
        Specification<Product> finalSpec = (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb)
                -> cb.like(root.get("title"), "%"+filter+"%");
        return finalSpec;
    }
    public static Specification<Product> minPriceFilter(Integer filter)
    {
        Specification<Product> finalSpec = (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb)
                -> cb.lessThanOrEqualTo(root.get("price"),filter);
        System.out.println(filter);
        return finalSpec;
    }

    }
