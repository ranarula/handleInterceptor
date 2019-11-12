package com.example.demo;

import com.example.demo.aop.AuthenticationAspect;
import com.example.demo.service.AuthenticationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.reactive.ServerWebExchangeContextFilter;
import org.springframework.web.server.WebFilter;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@Bean
	WebFilter serverWebExchangeContextFilter(){
		return new ServerWebExchangeContextFilter();
	}

	//Try to use Aspect to perform annotation processing
	@Bean
	AuthenticationAspect aspect(AuthenticationService service){
		return new AuthenticationAspect(service);
	}

	//Try to use WebFilter to perform annotation processing
	/*@Bean
	WebFilter authFilter(AuthenticationService service){
		return new AuthenticationFilter(service);
	}*/
}
