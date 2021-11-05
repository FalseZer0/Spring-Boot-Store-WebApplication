package com.geekbrains.demoboot.repositories;

import com.geekbrains.demoboot.entities.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>, JpaSpecificationExecutor {
    Optional<Product> findById(Long id);
//
    @Query(value = "SELECT p FROM Product p ORDER BY p.popularity DESC")
    List<Product> most3popular(Pageable pageable);
}
