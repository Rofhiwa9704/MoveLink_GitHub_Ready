package com.movelink.backend.service;

import com.movelink.backend.dto.QuoteRequest;
import com.movelink.backend.entity.Quote;

import java.util.List;

public interface QuoteService {

    Quote submitQuote(QuoteRequest request);

    List<Quote> getQuotes(Long rideId);

    Quote acceptQuote(Long quoteId);
}