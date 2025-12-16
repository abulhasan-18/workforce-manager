package com.abulhasan.workforcemanager.config;

import com.abulhasan.workforcemanager.service.EmployeeUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCrypt hash
    }

    @SuppressWarnings("deprecation")
@Bean
    public DaoAuthenticationProvider authenticationProvider(
            EmployeeUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   DaoAuthenticationProvider authProvider) throws Exception {

        http.authenticationProvider(authProvider);

        http
                // for HTML form app, keep CSRF enabled by default (good)
                .authorizeHttpRequests(auth -> auth
                        // allow static resources + login page
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/login").permitAll()

                        // Role-based access (you can adjust later)
                        .requestMatchers("/employees/new", "/employees/create").hasAnyRole("ADMIN", "HR")
                        .requestMatchers("/employees/*/edit", "/employees/*/update").hasAnyRole("ADMIN", "HR")
                        .requestMatchers("/employees/*/delete").hasRole("ADMIN")

                        // everything else must be logged in
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")     // POST here
                        .usernameParameter("email")       // our username is email
                        .passwordParameter("password")
                        .defaultSuccessUrl("/employees", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                )
                // optional: useful while building
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
