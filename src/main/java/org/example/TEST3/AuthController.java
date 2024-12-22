package org.example.TEST3;

import org.example.TEST3.User.User;
import org.example.TEST3.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;


@Controller
public class AuthController {
    private final ResourceLoader resourceLoader;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("User", new User());
        return "register";  // Страница для регистрации
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("GUEST");  // Устанавливаем роль по умолчанию как "GUEST"
        userService.saveUser(user);
        return "redirect:/login";  // Перенаправляем на страницу логина после успешной регистрации
    }

    @GetMapping("/login")
    public String login(Model model) {
        System.out.println("qweqweqwe");
        model.addAttribute("User", new User());
        return "login";
    }
    @GetMapping(value = "/login", produces = "text/css")
    public ResponseEntity<Resource> getCss() {
        Resource resource = resourceLoader.getResource("classpath:static/css/reg_n_log.css");
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("text/css"))  // Изменено здесь
                .body(resource);
    }

}
