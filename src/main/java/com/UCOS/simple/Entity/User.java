package com.UCOS.simple.Entity;

import com.UCOS.simple.DTO.UserInfo;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    private String username;
    private String password;
    private String authorities="reader";

    public User(UserInfo userInfo) {
        this.username = userInfo.getUsername();
        this.password = new BCryptPasswordEncoder().encode(userInfo.getPassword());
        this.authorities = userInfo.getAuthorities();
    }

    public UserInfo toUserInfo() {
        return UserInfo.builder()
                .username(username)
                .password(password)
                .authorities(authorities)
                .build();

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return username;
    }


}
