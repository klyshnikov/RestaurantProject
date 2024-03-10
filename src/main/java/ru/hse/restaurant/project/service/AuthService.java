package ru.hse.restaurant.project.service;


import ru.hse.restaurant.project.repository.AuthRepository;

public class AuthService {
    public AuthRepository authRepository;

    public Boolean register(String login, String password) {
        return true;
    }

    public Boolean login(String login, String password) {

    }

    public Boolean isEnterAsUser() {

    }

    public Boolean isEnterAsAdmin() {

    }
}
