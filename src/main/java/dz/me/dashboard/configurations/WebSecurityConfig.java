package dz.me.dashboard.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import dz.me.dashboard.filters.AuthEntryPointJwt;
import dz.me.dashboard.filters.JwtAuthorizationFilter;
import dz.me.dashboard.services.implement.UserDetailsServiceImpl;

/**
 *
 * @author MEKRICHE TAREK
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfig {

  UserDetailsServiceImpl userDetailsService;
  private AuthEntryPointJwt unauthorizedHandler;
  JwtAuthorizationFilter jwtAuthorizationFilter;

  public WebSecurityConfig(JwtAuthorizationFilter jwtAuthorizationFilter, AuthEntryPointJwt unauthorizedHandler,
      UserDetailsServiceImpl userDetailsService) {
    this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    this.unauthorizedHandler = unauthorizedHandler;
    this.userDetailsService = userDetailsService;
  }

  /*
   * @Bean
   * public AuthTokenFilter authenticationJwtTokenFilter() {
   * return new AuthTokenFilter();
   */

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/api/v1/services/**",
            "/api/v1/rubriques/**")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/user/info", "/api/foos/**")

    ;
    http.cors().and().csrf().disable()
        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .authorizeRequests()
        .antMatchers("/api/v1/auth/login/**", "/api/v1/auth/refresh-token**", "/guichet1",
            "/api/v1/guichets/all/user/**", "/")
        .permitAll()
        .anyRequest().authenticated();
    http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

}
