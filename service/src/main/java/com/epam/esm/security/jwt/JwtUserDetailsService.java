package com.epam.esm.security.jwt;

import com.epam.esm.constant.ColumnNames;
import com.epam.esm.entity.User;
import com.epam.esm.exception.NoSuchEntityException;;
import com.epam.esm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) {
        User user = userRepository.findByField(ColumnNames.LOGIN_COLUMN_LABEL, s)
                .orElseThrow(() -> new NoSuchEntityException("user.not.found"));

        JwtUser jwtUser = JwtUserFactory.create(user);
        return jwtUser;
    }
}
