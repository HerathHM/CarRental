package com.project.CarRental.service.auth;

import com.project.CarRental.dto.SignupRequest;
import com.project.CarRental.dto.UserDto;

public interface AuthService {

    UserDto createCustomer(SignupRequest signupRequest);

    boolean hasCustomerWithEmail(String email);
}
