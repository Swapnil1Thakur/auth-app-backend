package com.substring.auth.auth_app_backend.security;

import com.substring.auth.auth_app_backend.helpers.UserHelper;
import com.substring.auth.auth_app_backend.repositories.UserRepository;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //header check
        String header = request.getHeader("Authorization");

        // 2. check
        if (header != null && header.startsWith("Bearer")) {

            //token extract -> token validate -> authentication create -> set in authentication context

            //1. extracting token
            String token = header.substring(7);

            //check //work only if it is access token
            if(!jwtService.isAccessToken(token)){
                //message pass
                filterChain.doFilter(request, response);
                return;
            }

            //2. token parse (exception might come)
            try {
                //parse jwt token
                Jws<Claims> parse = jwtService.parse(token);

                Claims payload = parse.getPayload();
                String userId = payload.getSubject();
                UUID userUuid = UserHelper.parseUUID(userId);  //pasring (kyuki DB me uuid stored hai na)


                // Token se userId nikala → pata chala request kis user ki hai,
                // par token pe blind trust nahi (user exist/enable/roles check nahi hote),
                // isliye DB se user uthake verify karke hi authentication banate hain
                //so ->
                userRepository.findById(userUuid)
                        .ifPresent(user -> {

                            //check for user enable or not
                            if(user.isEnable()){
                                //user mil chuka h database se
                                //Agar roles null hain, toh .stream() se error aa sakta hai isliye empty list use karte hain (no permissions),
                                // aur agar roles present hain, toh unhe Spring Security ke samajhne wale format GrantedAuthority mein convert karte hain taaki system decide kar sake user kya actions perform kar sakta hai.
                                List<GrantedAuthority> authorities = user.getRoles() == null ? List.of() : user.getRoles().stream()
                                        .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());


                                //Authentication object bana ke Spring Security ko bataya jaata hai ki user kaun hai aur kya kar sakta hai

                                //creating authentication object
                                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                        user.getEmail(), null, authorities);

                                // ye line authentication ke saath request ki extra details (IP, session) attach karti hai"
                                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                                //Spring Security ke current request context me authentication set ho rahi hai
                                //mtlb -> Ye line batati hai ki current request ka user authenticated hai
                                //final line: to set the authentication to the security context
                                //null raha -> toh hi set krenge

                                if(SecurityContextHolder.getContext().getAuthentication()==null)
                                    SecurityContextHolder.getContext().setAuthentication(authentication);
                            }






                        });


            } catch (ExpiredJwtException e) {
                e.printStackTrace();

            } catch (MalformedJwtException e) {
                e.printStackTrace();

            } catch (JwtException e) {
                e.printStackTrace();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        //upar nahi chala
        filterChain.doFilter(request, response);
    }
}
