package com.project.CarRental.controller;

import com.project.CarRental.dto.AuthenticationRequest;
import com.project.CarRental.dto.AuthenticationResponse;
import com.project.CarRental.dto.SignupRequest;
import com.project.CarRental.dto.UserDto;
import com.project.CarRental.entity.User;
import com.project.CarRental.repository.UserRepository;
import com.project.CarRental.service.auth.AuthService;
import com.project.CarRental.service.jwt.UserService;
import com.project.CarRental.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final JWTUtil jwtUtil;

    private final UserRepository userRepository;



    @PostMapping("/signup")
    public ResponseEntity<?> signupCustomer(@RequestBody SignupRequest signupRequest){
        if(authService.hasCustomerWithEmail(signupRequest.getEmail()))
            return new ResponseEntity<>("Customer already exist with this email", HttpStatus.NOT_ACCEPTABLE);
        UserDto createdCustomerDto = authService.createCustomer(signupRequest);
        if(createdCustomerDto == null)return new ResponseEntity<>
                ("Customer Not Created Come Again Later", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(createdCustomerDto, HttpStatus.CREATED);
        }


        @PostMapping("/login")
        public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws
                BadCredentialsException,
                DisabledException,
                UsernameNotFoundException{
            try{
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()));
            }catch(BadCredentialsException e){
                throw new BadCredentialsException("Incorrect username or password");
            }
            final UserDetails userDetails = userService.userDetailService().loadUserByUsername(authenticationRequest.getEmail());
            Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
            final String jwt = jwtUtil.generateToken(userDetails);
            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
            if(optionalUser.isPresent()){
                authenticationResponse.setJwt(jwt);
                authenticationResponse.setUserId(optionalUser.get().getId());
                authenticationResponse.setUserRole(optionalUser.get().getUserRole());
            }
            return authenticationResponse;
        }





}
