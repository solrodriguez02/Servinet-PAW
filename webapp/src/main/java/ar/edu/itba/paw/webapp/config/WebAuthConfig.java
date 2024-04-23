package ar.edu.itba.paw.webapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;
@EnableWebSecurity
@ComponentScan({
        "ar.edu.itba.paw.webapp.auth"
})
@Configuration
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Value("${rememberMe.key}")
    private String rememberMeKey;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.sessionManagement()
            .and()
            .authorizeRequests()
                .antMatchers("/login", "/registrarse", "/olvide-mi-clave", "/restablecer-clave/**").anonymous()
                .antMatchers("/").permitAll()
                .antMatchers("/servinet/**").permitAll()
                .antMatchers("/servicios/**").permitAll()
                .antMatchers("/servicio/**").permitAll()
                .antMatchers("/usuario/**").permitAll()
//              .antMatchers("/cancelar-turno/**").access("hasRole('ROLE_USER') and @turnoServiceImpl.canCancelTurno(authentication, #id)")
                // TODO: ver endpoints para restringir el acceso
                .antMatchers("/**").authenticated().and()
            .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/", false) .and()
            .rememberMe()
                .userDetailsService(userDetailsService)
                .rememberMeParameter("remember-me").key(rememberMeKey)
                .tokenValiditySeconds((int) TimeUnit.HOURS.toSeconds(6)).and()
            .logout().logoutUrl("/logout").logoutSuccessUrl("/login").and()
                .exceptionHandling().accessDeniedPage("/403") .and()
            .csrf().disable();
    }
    @Override
    public void configure(final WebSecurity web) {
        web.ignoring().antMatchers("/resources/**", "/images/**","/css/**", "/js/**", "/img/**", "/favicon.ico");
    }
}
