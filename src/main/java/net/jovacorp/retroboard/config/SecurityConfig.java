package net.jovacorp.retroboard.config;

import net.jovacorp.retroboard.model.User;
import net.jovacorp.retroboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@ComponentScan("net.jovacorp.retroboard")

public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired private UserService userDetailsService;

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/h2-console/**");
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.formLogin()
        .and()
        .logout()
        .permitAll()
        .and()
        .authorizeRequests()
        .antMatchers("/**")
        .hasRole("USER");
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authenticationProvider());
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    authenticationProvider.setUserDetailsService(userDetailsService);
    return authenticationProvider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public ApplicationRunner applicationRunner() {
    return args -> {
      userDetailsService.create(new User(null, "shazin", passwordEncoder().encode("password"), "ROLE_USER"));
      userDetailsService.create(new User(null, "shahim", passwordEncoder().encode("password"), "ROLE_USER"));
    };
  }
}
