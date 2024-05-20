package com.example.model;

import java.time.LocalDateTime;

public record Message(
        String csrf,
        String acquirerId,
        String transactionId,
        String cardHolder,
        String cardPAN,
        Integer score,
        LocalDateTime createdAt
) { }