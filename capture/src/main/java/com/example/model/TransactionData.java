package com.example.model;

import java.util.Date;
import java.util.UUID;

public record TransactionData (
    String csrf,
    String acquirerId,
    UUID transactionId,
    String cardHolder,
    String cardPAN,
    Integer score,
    Date createdAt
) { }

