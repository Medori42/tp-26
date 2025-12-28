package com.medori42.inventory.service.service;

import com.medori42.inventory.service.domain.Book;
import com.medori42.inventory.service.repo.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing book-related operations.
 * Developed by Medori42.
 */
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final PricingClient pricingClient;

    /**
     * Constructs a {@link BookService} with the required dependencies.
     *
     * @param bookRepository the repository for book data
     * @param pricingClient  the client for retrieving pricing information
     */
    public BookService(BookRepository bookRepository, PricingClient pricingClient) {
        this.bookRepository = bookRepository;
        this.pricingClient = pricingClient;
    }

    /**
     * Retrieves all books in the inventory.
     *
     * @return a list of all {@link Book} entities
     */
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Creates a new book record.
     *
     * @param book the book entity to create
     * @return the saved {@link Book} entity
     * @throws IllegalArgumentException if a book with the same title already exists
     */
    public Book createBook(Book book) {
        bookRepository.findByTitle(book.getTitle()).ifPresent(existingBook -> {
            throw new IllegalArgumentException("A book with the same title already exists in the inventory.");
        });
        return bookRepository.save(book);
    }

    /**
     * Processes a book borrow operation.
     * This method handles stock decrementing and price fetching within a
     * transaction.
     *
     * @param bookId the ID of the book to borrow
     * @return a {@link InventoryOperationResult} containing the operation details
     * @throws IllegalArgumentException if the book is not found
     * @throws IllegalStateException    if there is no stock available
     */
    @Transactional
    public InventoryOperationResult borrowBook(long bookId) {
        // Apply pessimistic write lock at the database level
        Book book = bookRepository.findByIdForUpdate(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with ID: " + bookId));

        book.decrementStock(); // Decrements or throws exception if out of stock
        double currentPrice = pricingClient.getPrice(bookId);

        return new InventoryOperationResult(book.getId(), book.getTitle(), book.getStockQuantity(), currentPrice);
    }

    /**
     * Record representing the result of an inventory operation.
     */
    public record InventoryOperationResult(Long id, String title, int stockRemaining, double price) {
    }
}