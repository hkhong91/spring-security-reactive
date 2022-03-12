package com.example.sns.infrastructure.security;

import com.example.sns.domain.document.User;
import com.example.sns.domain.value.Authority;
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
public class SNSUserDetails implements UserDetails {

  private String id;
  private String name;
  private String email;
  private List<String> authorities;

  public static SNSUserDetails of(User user) {
    return SNSUserDetails.builder()
        .id(user.getId())
        .name(user.getName())
        .email(user.getEmail())
        .authorities(user.getAuthorities()
            .stream()
            .map(Authority::name)
            .collect(Collectors.toList()))
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
