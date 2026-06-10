package com.exchanger.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Long telegramChatId;
    private boolean active;
}