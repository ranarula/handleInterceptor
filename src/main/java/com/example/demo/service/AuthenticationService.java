package com.example.demo.service;

import com.example.demo.annotation.Authenticated;
import com.example.demo.dto.AuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

@Service
public class AuthenticationService {
  private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

  public Mono<AuthResponse> validateToken(ServerHttpRequest request, Authenticated auth) {
       var authResponse = new AuthResponse("Success");
      if(StringUtils.hasText(request.getHeaders().getFirst("FAIL"))){
        logger.info("In auth service");
        return Mono.error(new IllegalStateException("Auth Failure"));
    }
      return Mono.just(authResponse);
  }

}
