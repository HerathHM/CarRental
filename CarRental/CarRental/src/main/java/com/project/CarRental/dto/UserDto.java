package com.project.CarRental.dto;

import com.project.CarRental.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {

    private Long id;

    private String name;
    private String email;
    private UserRole userRole;
}
