package com.exchanger.dto;

import jakarta.validation.constraints.Email;

public record UserRequest (String firstName, String lastName, @Email String email, String phone) {}
