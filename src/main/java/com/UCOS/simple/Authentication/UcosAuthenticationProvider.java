package com.UCOS.simple.Authentication;

import com.UCOS.simple.Entity.User;
import com.UCOS.simple.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UcosAuthenticationProvider implements AuthenticationProvider {
    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try{
            User user = userService.loadUserByUsername(authentication.getName());
            if(new BCryptPasswordEncoder().matches(authentication.getCredentials().toString(), user.getPassword())){
                return new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword(),user.getAuthorities());
            }else {
                throw new BadCredentialsException("Invalid Credentials");
            }

        }catch (UsernameNotFoundException e){
            throw new BadCredentialsException("Invalid Credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
