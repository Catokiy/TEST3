package org.example.TEST3;

import org.example.TEST3.User.User;
import org.example.TEST3.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;


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

}
