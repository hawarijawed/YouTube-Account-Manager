package com.YoutubeAccount.Manager.config;

import com.YoutubeAccount.Manager.filter.JwtFilter;
import com.YoutubeAccount.Manager.service.UserDetailsImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {


    private final UserDetailsImpl userDetails;
    private final JwtFilter jwtFilter;
    public SpringSecurityConfig(UserDetailsImpl userDetails, JwtFilter filter){
        this.jwtFilter = filter;
        this.userDetails = userDetails;
    }
    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**").permitAll()
                                .requestMatchers("/api/user/login").permitAll()
                        .requestMatchers("/api/user/**").authenticated()
                        .requestMatchers("/api/video/**").authenticated()
                        .requestMatchers("/api/youtubeaccount/**").authenticated()
                        .requestMatchers("/api/comments/**").authenticated()


                )

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Important for JWT
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

}
