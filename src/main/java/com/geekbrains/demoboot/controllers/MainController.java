package com.geekbrains.demoboot.controllers;

import com.geekbrains.demoboot.entities.Cat;
import com.geekbrains.demoboot.entities.Product;
import com.geekbrains.demoboot.services.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping("/main")
public class MainController {
    CatService catService;

    @Autowired
    public void setCatService(CatService catService) {
        this.catService = catService;
    }

    @GetMapping
    public String index() {
        return "index";
    }
//
//    @GetMapping("/secured")
//    public String secured() {
//        return "index";
//    }
//

    @GetMapping("/login")
    public String loginPage()
    {
        return "login";
    }
    @GetMapping("/form")
    public String showForm() {
        return "simple-form";
    }

    @PostMapping("/form")
    public String saveForm(@RequestParam(value = "name") String name, @RequestParam(value = "email") String email) {
        System.out.println(name);
        System.out.println(email);
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String doSomething() {
        return "index";
    }

    @GetMapping("/hello")
    public String helloRequest(Model model, @RequestParam(value = "name") String name) {
        model.addAttribute("name", name);
        return "hello";
    }

    @GetMapping("/addcat")
    public String showAddCatForm(Model model) {
        Cat cat = new Cat(1L, null, null);
        model.addAttribute("cat", cat);
        model.addAttribute("pussies",catService.getAllProducts());
        return "cat-form";
    }


    @PostMapping("/addcat")
    public String showAddCatForm(@ModelAttribute(value = "cat") Cat cat) {
        catService.add(cat);
        System.out.println(cat.getId() + " " + cat.getName() + " " + cat.getColor());
        return "redirect:/addcat";
    }
}
