package com.lucas.domain.service;

import com.lucas.api.dtos.input.UserLoginInput;
import com.lucas.common.exceptions.AuthorizationException;
import com.lucas.domain.model.Role;
import com.lucas.domain.model.User;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class JwtService {

    @Inject
    UserService userService;

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    public List<Object> generateJwt(UserLoginInput userLoginInput) {
        User user = userService.searchByEmail(userLoginInput.getEmail()).get();

        if (!BcryptUtil.matches(userLoginInput.getPassword(), user.getPassword())){
            throw new AuthorizationException("Error when logging in. Invalid email or password");
        }


        Set<String> roles = user.getRoles().stream().map(Role::getRole).collect(Collectors.toSet());

        String token = Jwt.issuer(issuer)
                .upn(user.getEmail())
                .groups(roles)
                .subject(user.getUsername())
                .expiresIn(Duration.ofMinutes(20))
                .sign();

        return List.of(token, user);
    }
}
