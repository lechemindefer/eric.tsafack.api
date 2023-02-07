package eric.tsafack.api.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    //Authentication
    public UserDetailsService userDetailsService(PasswordEncoder encoder){

        UserDetails admin = User.withUsername("eric")
                .password(encoder.encode("Pdw1"))
                .roles("ADMIN")
                .build();

        UserDetails user = User.withUsername("annie")
                .password(encoder.encode("Pdw2"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(admin,user);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       return  http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
               .and().csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("/", "/**", "/console/**").permitAll()
                .and().headers()
                .frameOptions()
                .disable()
                .and()
                .authorizeHttpRequests()
                .antMatchers("/users/welcome").permitAll()
                .and()
                .authorizeHttpRequests().antMatchers("/users/**")
                .authenticated().and().formLogin()
                .and().build();
    }
}
