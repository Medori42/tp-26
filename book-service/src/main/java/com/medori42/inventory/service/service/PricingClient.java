package com.medori42.inventory.service.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Client for interacting with the Pricing Service.
 * Implements resilience patterns using Resilience4j.
 * Developed by Medori42.
 */
@Component
public class PricingClient {

    private final RestTemplate restTemplate;
    private final String pricingServiceBaseUrl;

    /**
     * Constructs a {@link PricingClient} with the specified RestTemplate and base
     * URL.
     *
     * @param restTemplate          the RestTemplate to use for HTTP requests
     * @param pricingServiceBaseUrl the base URL of the Pricing Service
     */
    public PricingClient(RestTemplate restTemplate,
            @Value("${pricing.base-url}") String pricingServiceBaseUrl) {
        this.restTemplate = restTemplate;
        this.pricingServiceBaseUrl = pricingServiceBaseUrl;
    }

    /**
     * Retrieves the price of a book from the Pricing Service.
     * Includes retry and circuit breaker logic.
     *
     * @param bookId the ID of the book
     * @return the price of the book, or a fallback value if the service is
     *         unavailable
     */
    @Retry(name = "pricing")
    @CircuitBreaker(name = "pricing", fallbackMethod = "fallbackPrice")
    public double getPrice(long bookId) {
        String url = pricingServiceBaseUrl + "/api/prices/" + bookId;
        Double price = restTemplate.getForObject(url, Double.class);
        return price == null ? 0.0 : price;
    }

    /**
     * Fallback method used when the Pricing Service call fails or the circuit is
     * open.
     *
     * @param bookId    the ID of the book
     * @param throwable the exception that triggered the fallback
     * @return a default price value (0.0)
     */
    public double fallbackPrice(long bookId, Throwable throwable) {
        return 0.0;
    }
}