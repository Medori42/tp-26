package com.medori42.inventory.service.domain;

import jakarta.persistence.*;

/**
 * Represents a Book entity in the inventory.
 * Developed by Medori42.
 */
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 120)
    private String title;

    @Column(nullable = false, length = 80)
    private String author;

    @Column(nullable = false)
    private int stockQuantity;

    /**
     * Default constructor for JPA.
     */
    public Book() {
    }

    /**
     * Constructs a new Book with the specified details.
     *
     * @param title         the title of the book
     * @param author        the author of the book
     * @param stockQuantity the initial stock quantity
     */
    public Book(String title, String author, int stockQuantity) {
        this.title = title;
        this.author = author;
        this.stockQuantity = stockQuantity;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    /**
     * Decrements the stock quantity by one.
     *
     * @throws IllegalStateException if the stock is already zero or less
     */
    public void decrementStock() {
        if (this.stockQuantity <= 0) {
            throw new IllegalStateException("Out of stock: No more copies available");
        }
        this.stockQuantity--;
    }
}