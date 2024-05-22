package com.example.service;

import com.example.model.TransactionData;
import io.micrometer.observation.annotation.Observed;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@Observed
public class CaptureService {

    public TransactionData initializeTransaction(TransactionData data) {
        var txID = java.util.UUID.randomUUID();
        return new TransactionData(data.csrf(), data.acquirerId(), txID, data.cardHolder(), data.cardPAN(), data.score(), new Date());
    }

}
