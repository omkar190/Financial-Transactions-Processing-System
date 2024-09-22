package org.safepay.configurations.dev;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Profile("dev")
public class WebSecurityConfigDevEnv {

    @Value("${default.user}")
    String defaultUsername;

    @Value("${default.password}")
    String defaultPassword;

    @Value("${default.roles}")
    String defaultRoles;

    @Bean
    public BCryptPasswordEncoder defaultPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //Enforces Spring Security on accessing APIs
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/OK","/h2-console/**", "/verify-account/new-user").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .csrf(csrf  -> csrf.ignoringRequestMatchers("/**"))
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        return http.build();
    }

    //Temporary, for H2-Console and database testing
    @Bean
    public UserDetailsService userDetailsService(){

        UserDetails user = User.builder()
                .username(defaultUsername)
                .password(defaultPasswordEncoder().encode(defaultPassword))
                .roles(defaultRoles)
                .build();

        return new InMemoryUserDetailsManager(user);
    }

}
