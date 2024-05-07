package it.juan.user.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //Acceso a Swagger: http://localhost:5000/swagger-ui.html
    private static final String[] AUTH_WHITELIST = {
// -- Swagger UI v2 "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
// -- Swagger UI v3 (OpenAPI) "/v3/api-docs/**",
            "/swagger-ui/**" }; // other public endpoints of your API may be appended to this array };
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api_hoteles/user").permitAll()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Ignorar estas rutas para la autenticación
                .anyRequest().authenticated();
    }
}
