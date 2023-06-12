package com.example.mobile_banking_api.security;

import com.example.mobile_banking_api.api.auth.AuthMapper;
import com.example.mobile_banking_api.api.user.Authority;
import com.example.mobile_banking_api.api.user.Role;
import com.example.mobile_banking_api.api.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AuthMapper authMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("hello");
        User user = authMapper.loadUserByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User is not valid"));

       for (Role role : user.getRoles()){
           for (Authority authority : role.getAuthorities()){
               System.out.println(authority.getName());
           }
       }
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUser(user);

        log.info("Custom User Detail: {}",customUserDetails.getAuthorities());
        System.out.println(user);
        return customUserDetails;
    }
}
