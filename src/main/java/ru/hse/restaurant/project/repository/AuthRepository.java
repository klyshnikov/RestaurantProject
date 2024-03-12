package ru.hse.restaurant.project.repository;

import ru.hse.restaurant.project.entity.User;

import java.io.IOException;

public interface AuthRepository {
    void addUser(User user) throws IOException;
    Boolean isExist(String login, String password) throws IOException;
    User getUser(String login, String password) throws IOException;
}
