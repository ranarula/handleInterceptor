package com.example.demo.filter;

import com.example.demo.annotation.Authenticated;
import com.example.demo.service.AuthenticationService;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter implements WebFilter {

    private final AuthenticationService validator;

    public AuthenticationFilter(AuthenticationService validator){
      this.validator = validator;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
      //Need Help here. The validation should happen before the controller method is processed
      return chain.filter(exchange)
          .doOnSubscribe(aVoid -> validate(exchange, "sub"))
          .doOnNext(aVoid -> validate(exchange,"next"))
          .doOnEach(aVoid -> validate(exchange, "each"))
          .doFirst(() -> validate(exchange," first"))
          .doOnSuccess(aVoid -> validate(exchange,"success"))
          .doOnError(aVoid -> validate(exchange,"error"));
    }

    void validate(ServerWebExchange exchange, String op){
      System.out.println("op "+ op+" Handler "+ exchange.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE));

       Optional
          .ofNullable(exchange.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE))
          .filter(HandlerMethod.class::isInstance)
          .map(HandlerMethod.class::cast)
          .map(handlerMethod -> handlerMethod.getMethod().getAnnotation(Authenticated.class))
          .filter(Objects::nonNull)
          .map(annotation -> validator.validateToken(exchange.getRequest(), annotation));
           //Throw error on validation failure
    }
}


