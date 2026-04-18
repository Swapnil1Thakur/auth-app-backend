package com.substring.auth.auth_app_backend.exceptions;

import com.substring.auth.auth_app_backend.dtos.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //
    @ExceptionHandler({
           UsernameNotFoundException.class,
           BadCredentialsException.class,
            CredentialsExpiredException.class,
            ExpiredJwtException.class,
            JwtException.class,
            AuthenticationException.class             //writing exceptions
    })
    public ResponseEntity<ApiError> handleAuthException(Exception e, HttpServletRequest request){



    }

    //resource not found exception handler :: method
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception){
        ErrorResponse internalServerError = new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND, 404);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(internalServerError);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception){
        ErrorResponse internalServerError = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST, 400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(internalServerError);
    }




}
