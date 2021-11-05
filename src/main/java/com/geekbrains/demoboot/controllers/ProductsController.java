package com.geekbrains.demoboot.controllers;

import com.geekbrains.demoboot.entities.Product;
import com.geekbrains.demoboot.repositories.specifications.ProductSpecs;
import com.geekbrains.demoboot.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductsController {
    @GetMapping("/failure")
    public String failPage() {
        return "redirect:/products";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }

    private ProductsService productsService;

    @Autowired
    public void setProductsService(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping
    public String showProductsList(Principal principal, Model model,
                                   @RequestParam(value = "strFilter", required = false) String filter,
                                   @RequestParam(value = "minFilter", required = false) Integer minprice,
                                   @RequestParam(value = "pageNo", required = false) Optional<Integer> pageNo) {
        int page = 0;
        if (principal != null) {
//            System.out.println(principal.getName());
        }
        if (pageNo.isPresent()) {
            page = pageNo.get();
        }
        Product product = new Product();
        Specification<Product> specification = Specification.where(null);
        if (filter != null) {
            System.out.println(1);
            specification = specification.and(ProductSpecs.productFiltered(filter));
        } // and returns new object but doesnt change current
        if (minprice != null) {
            System.out.println(2);
            specification = specification.and(ProductSpecs.minPriceFilter(minprice));
        }
        model.addAttribute("products", productsService.getAllProducts(specification, PageRequest.of(page, 2)).getContent());
        model.addAttribute("product", product);
        model.addAttribute("filter", filter);
        model.addAttribute("minprice", minprice);
        model.addAttribute("pages", productsService.getAllProducts(specification, PageRequest.of(page, 2)).getTotalPages());
        return "products";
    }
    @GetMapping("/popular")
    public String popular(Model model)
    {
        model.addAttribute("products",productsService.mostPopularProducts());
//        model.addAttribute("products",productsService.popularProducts());
        return "popular";
    }
    @GetMapping("/test")
    public String test(Model model, @RequestParam(value = "sexys") String ss) {
        model.addAttribute("lulzy", ss);
        return "sexy";
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/add")
    public String addProduct(@ModelAttribute(value = "product") Product product) {
        productsService.add(product);
        return "redirect:/products";
    }

    //    @Secured({"ROLE_ADMIN"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/edit/{id}")
    public String editProduct(Model model, @PathVariable(value = "id") Long id) {
        Product dummy = new Product();
        dummy.setId(id);
        model.addAttribute("dummy", dummy);
        return "product-edit";
    }

    @PostMapping("/edit")
    public String editUpdateProduct(@ModelAttribute(value = "dummy") Product dummy) {
        System.out.println(dummy.getTitle());
        Product product = productsService.getById(dummy.getId());
        product.setPrice(dummy.getPrice());
        product.setTitle(dummy.getTitle());
        productsService.add(product);
        return "redirect:/products";
    }


    //    @Secured({"ROLE_ADMIN"})
//@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/show/{id}")
    public String showOneProduct(Model model, @PathVariable(value = "id") Long id) {
        Product product = productsService.getById(id);
        productsService.incrementPopularity(id);
        model.addAttribute("product", product);
        return "product-page";
    }

    //    @Secured({"ROLE_ADMIN"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable(value = "id") Long id) {
        Product product = productsService.getById(id);
        productsService.delete(product);
        return "redirect:/products";
    }

    @GetMapping("/logout")
    public String userLogout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return "redirect:/products";
    }
}
