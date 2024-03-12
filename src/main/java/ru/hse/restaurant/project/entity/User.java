package ru.hse.restaurant.project.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
public class User {
    //private static final long serialVersionUID = 1L;
    public String name;

    public String login;

    public String password;

    public String role;

}
