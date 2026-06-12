package com.exchanger.dto;

import com.exchanger.validator.Phone;
import jakarta.validation.constraints.Email;

public record UserRequest (String firstName, String lastName, @Email String email, @Phone String phone) {}
