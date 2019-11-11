package com.example.demo;

import com.example.demo.annotation.Authenticated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class DemoController {


  @GetMapping
  @Authenticated
  public Mono<String> helloWorld(){
    System.out.println("in  API");
    return Mono.just("Hello World");
  }
}
