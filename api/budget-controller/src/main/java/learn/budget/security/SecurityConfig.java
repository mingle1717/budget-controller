package learn.budget.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtFailedAuthEntryPoint entryPoint;

    @Autowired
    AuthTokenFilter filter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors();

        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS );

        http.authorizeRequests()
                .antMatchers( HttpMethod.GET, "/api/public" ).permitAll()
                .antMatchers( HttpMethod.POST, "/api/security").permitAll()
                .antMatchers( HttpMethod.GET, "/api/budget/personal/*").hasAnyRole("Admin", "Member")
                .antMatchers( HttpMethod.POST, "/api/budget").hasRole("Member")
                .antMatchers( HttpMethod.GET, "/api/transaction").hasAnyRole("Admin", "Member")
                .antMatchers( HttpMethod.POST, "/api/transaction").hasAnyRole("Admin", "Member")
                .antMatchers( HttpMethod.GET, "/api/transaction/*").hasAnyRole("Admin", "Member")
                .antMatchers( HttpMethod.DELETE, "/api/transaction/*").hasAnyRole("Admin", "Member")
                .antMatchers( HttpMethod.POST, "/api/security/register").permitAll()

                .anyRequest().denyAll();

        http.addFilterBefore( filter, UsernamePasswordAuthenticationFilter.class );
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
