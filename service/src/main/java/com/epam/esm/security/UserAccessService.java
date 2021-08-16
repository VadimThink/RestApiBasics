package com.epam.esm.security;

import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.UserResponseDto;
import com.epam.esm.logic.UserService;
import com.epam.esm.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class UserAccessService {

    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;

    public UserAccessService(JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    public void checkAccess(HttpServletRequest httpServletRequest, long userId) {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String login = jwtTokenProvider.getLogin(token);
        List<String> roles = jwtTokenProvider.getRoles(token);
        UserResponseDto userDto = userService.getByLogin(login);
        if ((userDto.getId() != userId) && !roles.contains("ROLE_ADMIN")) {
            throw new AuthorizationServiceException("exception.no.access");
        }
    }
}
