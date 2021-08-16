package com.epam.esm.controller;

import com.epam.esm.dto.AuthenticationRequestDto;
import com.epam.esm.dto.AuthenticationResponseDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.UserResponseDto;
import com.epam.esm.logic.UserService;
import com.epam.esm.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@Profile("prod")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping
    @RequestMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponseDto login(@RequestBody AuthenticationRequestDto requestDto){
        String login = requestDto.getLogin();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, requestDto.getPassword()));
        UserResponseDto userDto = userService.getByLogin(login);
        String token = jwtTokenProvider.createToken(userDto);
        return new AuthenticationResponseDto(login, token);
    }
}
