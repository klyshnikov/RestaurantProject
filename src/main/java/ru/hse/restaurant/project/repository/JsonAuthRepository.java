package ru.hse.restaurant.project.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.hse.restaurant.project.entity.AllUsers;
import ru.hse.restaurant.project.entity.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class JsonAuthRepository implements AuthRepository {

    public ObjectMapper jsonMapper = new ObjectMapper();

    public String getFile() {
        return "/home/misha/IdeaProjects/project/src/main/resources/user.data/users.json";
    }

    public List<User> getUsers() throws IOException {
//        Path path = Paths.get(getFile());
//        var v = Files.readAllLines(path);
//        if (v.isEmpty()) {
//            return new ArrayList<User>() {};
//        }
//        String content = Files.readAllLines(path).get(0);
//        ObjectMapper mapper = new ObjectMapper();
//        ListUsers users = mapper.readValue(content, ListUsers.class);
//        return users.users;

        AllUsers users = jsonMapper.readValue(new File(getFile()), AllUsers.class);
        return users.users;
    }

    public void setUser(List<User> users) throws IOException {
        AllUsers listUsers = new AllUsers();
        listUsers.users = users;
        jsonMapper.writeValue(new File(getFile()), listUsers);

        AllUsers written = jsonMapper.readValue(new File(getFile()), AllUsers.class);
    }

    @Override
    public void addUser(User user) throws IOException {
        List<User> users = getUsers();
        users.add(user);
        setUser(users);
    }

    @Override
    public Boolean isExist(String name) throws IOException {
        List<User> users = getUsers();
        for (User user : users) {
            if (Objects.equals(user.name, name)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Boolean isExist(String login, String password) throws IOException {
        List<User> users = getUsers();
        for (User user : users) {
            if (Objects.equals(user.login, login) && Objects.equals(user.password, password)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public User getUser(String login, String password) throws IOException {
        List<User> users = getUsers();
        for (User user : users) {
            if (Objects.equals(user.login, login) && Objects.equals(user.password, password)) {
                return user;
            }
        }

        return null;
    }
}
