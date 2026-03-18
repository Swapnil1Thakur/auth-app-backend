package com.substring.auth.auth_app_backend.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Getter@Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class RoleDto {

    private UUID id ;
    private String name;
}
