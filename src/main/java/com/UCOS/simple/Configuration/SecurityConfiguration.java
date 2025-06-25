package com.UCOS.simple.Configuration;

import com.UCOS.simple.Authentication.UcosAuthenticationProvider;
import com.UCOS.simple.Authentication.UcosAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UcosAuthenticationProvider authenticationProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                authorizeRequests -> authorizeRequests.requestMatchers("/swagger-ui/**")
                        .authenticated()
                        .requestMatchers("/v3/api-docs*/**")
                        .authenticated()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .successHandler(new UcosAuthenticationSuccessHandler())
                )
                .authenticationProvider(authenticationProvider)
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

}
