package ru.hse.restaurant.project.service;


import org.springframework.stereotype.Component;
import ru.hse.restaurant.project.entity.Role;
import ru.hse.restaurant.project.entity.User;
import ru.hse.restaurant.project.repository.AuthRepository;
import ru.hse.restaurant.project.repository.JsonAuthRepository;

import java.io.Console;
import java.io.IOException;
import java.util.Objects;

@Component
public class AuthService {
    public AuthRepository authRepository = new JsonAuthRepository();
    public User user = null;

    public Boolean register(User user) throws IOException {
        if (!authRepository.isExist(user.name)) {
            authRepository.addUser(user);
            this.user = user;
            return true;
        }

        return false;
//        authRepository.addUser(user);
//        this.user = user;
//        return true;
    }

    public Boolean login(String login, String password) throws IOException {
        if (authRepository.isExist(login, password)) {
            user = authRepository.getUser(login, password);
            return true;
        }

        return false;
    }

    public void logout() {
        user = null;
    }

    public Boolean isEnterAsUser() {
        return user != null && Objects.equals(user.role, "Client");
    }

    public Boolean isEnterAsAdmin() {
        return user != null && Objects.equals(user.role, "Admin");
    }
}
