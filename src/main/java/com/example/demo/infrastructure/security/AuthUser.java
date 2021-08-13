package com.example.demo.infrastructure.security;

import com.example.demo.domain.book.document.sub.Creator;
import com.example.demo.domain.user.document.User;
import com.example.demo.domain.user.value.Authority;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class AuthUser implements UserDetails {

  private final String id;
  private final String name;
  private final String email;
  private final List<String> authorities;

  public static AuthUser of(User user) {
    return AuthUser.builder()
        .id(user.getId())
        .name(user.getName())
        .email(user.getEmail())
        .authorities(user.getAuthorities()
            .stream()
            .map(Authority::name)
            .collect(Collectors.toList()))
        .build();
  }

  public Creator toCreator() {
    return Creator.builder()
        .id(this.id)
        .name(this.name)
        .build();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities.stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return false;
  }
}
