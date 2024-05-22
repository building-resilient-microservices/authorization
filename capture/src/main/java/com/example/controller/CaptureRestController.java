package com.example.controller;

import com.example.model.TransactionData;
import com.example.service.CaptureService;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequiredArgsConstructor
@Observed
public class CaptureRestController {

    private final CaptureService captureService;

    @GetMapping("/sleep")
    @SneakyThrows
    public String sleep(@RequestParam long time) {
        TimeUnit.MILLISECONDS.sleep(time);
        return MessageFormat.format("Woke up after {0} milliseconds", time);
    }

    @PostMapping("/capture")
    public TransactionData capture(@RequestBody TransactionData transactionData) {
        return captureService.initializeTransaction(transactionData);
    }

}
