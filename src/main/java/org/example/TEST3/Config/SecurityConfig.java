package org.example.TEST3.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private org.example.TEST3.User.UserService UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/register", "/login").permitAll()  // Разрешить доступ к регистрационным страницам
                                .requestMatchers("/user_administration", "/edit_user/**").hasRole("ADMIN")  // Только для администраторов
                                .requestMatchers("/","/edit/","/cancel-edit/","/save-edited","/find").hasAnyRole("MANAGER", "ADMIN")  // Только для менеджеров
                                .requestMatchers("/new", "/save").hasAnyRole("CUSTOMER", "MANAGER", "ADMIN")  // Только для заказчиков
                                .requestMatchers("/welcome").hasAnyRole("GUEST","CUSTOMER", "MANAGER", "ADMIN")  // Только для заказчиков
                                .anyRequest().authenticated()  // Для всех остальных страниц — требуется аутентификация
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/welcome",true)
                        .permitAll())
                .logout(withDefaults())
                .exceptionHandling(exception -> exception.accessDeniedPage("/permission_denied"));


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Хеширование паролей
    }
}
