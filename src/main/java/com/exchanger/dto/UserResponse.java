package com.exchanger.dto;

import java.util.UUID;

public record UserResponse (UUID userId,
                            String firstName,
                            String lastName,
                            String email,
                            String phone,
                            boolean active) {}