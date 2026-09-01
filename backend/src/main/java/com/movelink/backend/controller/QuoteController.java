package com.movelink.backend.controller;

import com.movelink.backend.dto.QuoteRequest;
import com.movelink.backend.entity.Quote;
import com.movelink.backend.service.QuoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quotes")
@CrossOrigin(origins = "*")
public class QuoteController {

    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @PostMapping
    public ResponseEntity<Quote> submitQuote(
            @RequestBody QuoteRequest request) {

        return ResponseEntity.ok(
                quoteService.submitQuote(request));
    }

    @GetMapping("/ride/{rideId}")
    public ResponseEntity<List<Quote>> getQuotes(
            @PathVariable Long rideId) {

        return ResponseEntity.ok(
                quoteService.getQuotes(rideId));
    }

    @PutMapping("/{quoteId}/accept")
    public ResponseEntity<Quote> acceptQuote(
            @PathVariable Long quoteId) {

        return ResponseEntity.ok(
                quoteService.acceptQuote(quoteId));
    }
}