package com.example.demo.aop;

import com.example.demo.annotation.Authenticated;
import com.example.demo.service.AuthenticationService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.filter.reactive.ServerWebExchangeContextFilter;
import reactor.core.publisher.Mono;


 @Aspect
  public class AuthenticationAspect {

    private final AuthenticationService validator;

    public AuthenticationAspect(AuthenticationService validator) {
      this.validator = validator;
    }

    @Pointcut("@annotation(com.example.demo.annotation.Authenticated)")
    public void authValidator() { }

    @Around("authValidator()")
    public Object doAuth(ProceedingJoinPoint joinPoint) throws Throwable{
      MethodSignature ms = (MethodSignature) joinPoint.getSignature();
      Authenticated annotation = ms.getMethod().getAnnotation(Authenticated.class);

      //HELP : Unable to get the exchange from subscriberContext set by ServerWebExchangeContextFilter
      Mono.subscriberContext().map(context -> {
        ServerWebExchangeContextFilter.get(context).ifPresent(exchange ->{
            System.out.println(exchange.getRequest().getHeaders().toSingleValueMap());
            this.validator.validateToken(exchange.getRequest(), annotation);});
        return context;
      });



      joinPoint.proceed();
      return Mono.empty();

    }




  }
