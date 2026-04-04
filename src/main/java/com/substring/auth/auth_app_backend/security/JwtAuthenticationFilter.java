package com.substring.auth.auth_app_backend.security;

import com.substring.auth.auth_app_backend.helpers.UserHelper;
import com.substring.auth.auth_app_backend.repositories.UserRepository;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private  final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //header check
        String header = request.getHeader("Authorization");

        // 2. check
        if(header!=null && header.startsWith("Bearer" )){

            //token extract -> token validate -> authentication create -> set in authentication context

            //1. extracting token
            String token = header.substring(7);

            //2. token parse (exception might come)
            try{
                //parse jwt token
                Jws<Claims> parse = jwtService.parse(token);

                Claims payload = parse.getPayload();
                String userId = payload.getSubject();
                UUID userUuid = UserHelper.parseUUID(userId);  //pasring (kyuki DB me uuid stored hai na)


                // Token se userId nikala → pata chala request kis user ki hai,
                // par token pe blind trust nahi (user exist/enable/roles check nahi hote),
                // isliye DB se user uthake verify karke hi authentication banate hain






            }catch(ExpiredJwtException e){
                e.printStackTrace();

            }catch (MalformedJwtException e){
                e.printStackTrace();

            }catch(JwtException e){
                e.printStackTrace();

            }catch(Exception e){
                e.printStackTrace();
            }

        }

        //upar nahi chala
        filterChain.doFilter(request,response);
    }
}
