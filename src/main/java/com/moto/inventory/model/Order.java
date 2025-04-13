package com.moto.inventory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToMany
    @JoinTable(
            name = "order_motorcycle",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "motorcycle_num_chassis")
    )
    private Set<Motorcycle> motorcycles = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "order_piece",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "piece_id")
    )
    private Set<PieceMoto> pieces = new HashSet<>();

    @NotNull(message = "Total price is required")
    @PositiveOrZero(message = "Total price must be positive or zero")
    private BigDecimal totalPrice;

    @NotNull(message = "Date is required")
    @PastOrPresent(message = "Date cannot be in the future")
    private LocalDate date;

    // Constructors
    public Order() {
    }

    public Order(Client client, BigDecimal totalPrice, LocalDate date) {
        this.client = client;
        this.totalPrice = totalPrice;
        this.date = date;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Motorcycle> getMotorcycles() {
        return motorcycles;
    }

    public void setMotorcycles(Set<Motorcycle> motorcycles) {
        this.motorcycles = motorcycles;
    }

    public Set<PieceMoto> getPieces() {
        return pieces;
    }

    public void setPieces(Set<PieceMoto> pieces) {
        this.pieces = pieces;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    // Helper methods
    public void addMotorcycle(Motorcycle motorcycle) {
        motorcycles.add(motorcycle);
        motorcycle.getOrders().add(this);
    }

    public void removeMotorcycle(Motorcycle motorcycle) {
        motorcycles.remove(motorcycle);
        motorcycle.getOrders().remove(this);
    }

    public void addPiece(PieceMoto piece) {
        pieces.add(piece);
        piece.getOrders().add(this);
    }

    public void removePiece(PieceMoto piece) {
        pieces.remove(piece);
        piece.getOrders().remove(this);
    }
}