package com.Angelh0.stayhub.security;

import com.Angelh0.stayhub.security.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(csrf -> csrf.disable())

                    .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                    .authorizeHttpRequests(auth -> auth

                            .requestMatchers("/h2-console/**", "/api/v1/admin/getAllRooms", "/api/v1/admin/getAccommodation", "/api-docs/**").permitAll()

                            // Endpoints OWNER
                            .requestMatchers("/api/v1/create", "/api/v1/modified/**", "/api/v1/createRoom/**", "/api/v1/modifiedRoom/**", "/api/v1/room-block/**").hasRole("OWNER")

                            // Endpoints USER
                            .requestMatchers("/api/v1/searchAdvanced/**", "/api/v1/searchAdvancedRoom/**").permitAll()

                            .anyRequest().authenticated()
                    )


                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                    .headers(headers -> headers.frameOptions(frame -> frame.disable()));


            http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

            return http.build();
        }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "PATCH", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
