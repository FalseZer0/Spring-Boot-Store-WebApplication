package com.geekbrains.demoboot.services;

import com.geekbrains.demoboot.entities.Product;
import com.geekbrains.demoboot.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;



@Service
public class ProductsService {
    private ProductRepo productRepo;

    @Autowired
    public void setProductRepo(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public Product getById(Long id) {
        Product temp = productRepo.findById(id).get();
        //alternatively Product temp = productRepo.findById(id).orElse(null);
        return temp;
    }

    public Page<Product> getAllProducts(Specification<Product> specoficiation, Pageable pageable) {
        return productRepo.findAll(specoficiation, pageable);
    }

    public void add(Product product) {
        productRepo.save(product);
    }

    public void delete(Product product) {
        productRepo.delete(product);
    }


    @Transactional
    public void incrementPopularity(Long id)
    {
        Product product = productRepo.findById(id).get();
        Integer count = product.getPopularity();
        count++;
        product.setPopularity(count);
        productRepo.save(product); // redundant?
    }
    public List<Product> popularProducts()
    {
        return productRepo.findAll(Sort.by(Sort.Direction.DESC,"popularity"));
    }

    public List<Product> mostPopularProducts()
    {
        return productRepo.most3popular(PageRequest.of(0,3));
    }
}
