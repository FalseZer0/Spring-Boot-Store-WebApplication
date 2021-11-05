package com.geekbrains.demoboot.repositories.productaspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProductAspect {
    @Pointcut("execution(public String com.geekbrains.demoboot.controllers.ProductsController.showOneProduct(..))")
    private void getShow(){};

    @Before("getShow()")
    public void logBefore(JoinPoint joinPoint) {
//        System.out.println(joinPoint);
    }

}
