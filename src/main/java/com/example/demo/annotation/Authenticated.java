package com.example.demo.annotation;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Authenticated {

  String[] requiredHeaders() default {};

}