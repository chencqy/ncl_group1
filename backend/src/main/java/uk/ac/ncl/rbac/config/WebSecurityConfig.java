package uk.ac.ncl.rbac.config;

import uk.ac.ncl.rbac.filter.JwtAuthenticationFilter;
import uk.ac.ncl.rbac.filter.JwtLoginFilter;
import uk.ac.ncl.rbac.service.LoginCountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    public static String ADMIN = "ROLE_BuildingManager";
    public static String RESEARCHER = "ROLE_Researcher";
    public static String STUDENT = "ROLE_Student";
    public static String USER = "ROLE_MemberOfPublic";

    private final LoginCountService loginCountService;

    @Resource
    private CustomUserDetailsService customUserDetailsService;

    private final static String[] PERMIT_ALL_MAPPING = {
            "/api/login",
            "/api/home",
            "/api/public/**",
            "/api/user/**"
    };

    public WebSecurityConfig(LoginCountService loginCountService) {
        this.loginCountService = loginCountService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        List<String> allowedOriginsUrl = new ArrayList<>();
        allowedOriginsUrl.add("http://18.132.43.65:8090");
        allowedOriginsUrl.add("http://18.132.43.65:8080");
        allowedOriginsUrl.add("http://localhost:8090");
        allowedOriginsUrl.add("http://localhost:8080");
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(allowedOriginsUrl);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(PERMIT_ALL_MAPPING)
                .permitAll()
                .antMatchers("/api/student/**")
                .hasAnyAuthority(STUDENT)
                .antMatchers("/api/researcher/**")
                .hasAnyAuthority(RESEARCHER)
                .antMatchers("/api/admin/**")
                .hasAnyAuthority(ADMIN)
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(new JwtLoginFilter("/api/login", authenticationManager(), loginCountService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .cors()
                .and()
                .csrf()
                .ignoringAntMatchers(PERMIT_ALL_MAPPING)
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.ignoring().antMatchers( "/swagger-ui.html",
                "/swagger-ui/*",
                "/swagger-resources/**",
                "/v2/api-docs",
                "/v3/api-docs",
                "/webjars/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(customUserDetailsService);
        return provider;
    }
}
