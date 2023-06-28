package com.projet.artisan.securité;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class jwtAuthentificationFilter extends UsernamePasswordAuthenticationFilter {
  private  final AuthenticationManager authenticationManager;

  public jwtAuthentificationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    System.out.println("attemptAuthentication");
    String username=request.getParameter("username");
    String password=request.getParameter("password");

    System.out.println(username);
    System.out.println(password);

    UsernamePasswordAuthenticationToken authenticationToken=
            new UsernamePasswordAuthenticationToken(username,password);
         return authenticationManager.authenticate(authenticationToken);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
       System.out.print("successfulAuthentication");
       User user = (User) authResult.getPrincipal();
      Algorithm algo1=Algorithm.HMAC256("mysecret123");
      String jwtAccessToken = JWT.create()
              .withSubject(user.getUsername())
                      .withExpiresAt(new Date(System.currentTimeMillis()+5*60*1000))
                              .withIssuer(request.getRequestURL().toString())
                                      .withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                                              .sign(algo1);
      String jwtRefreshAccessToken = JWT.create()
              .withSubject(user.getUsername())
              .withExpiresAt(new Date(System.currentTimeMillis()+15*60*1000))
              .withIssuer(request.getRequestURL().toString())
              .sign(algo1);
      Map<String,String> idToken =new HashMap<>();
      idToken.put("access-token",jwtAccessToken);
      idToken.put("refresh-token",jwtRefreshAccessToken);
      response.setContentType("application/json");
      new ObjectMapper().writeValue(response.getOutputStream(),idToken);
    }
}
