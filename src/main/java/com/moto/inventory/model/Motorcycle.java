package com.moto.inventory.model;

import jakarta.persistence.*;
import jakarta.persistence.Basic;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import com.fasterxml.jackson.annotation.JsonProperty;
 

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "motorcycles")
public class Motorcycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String numChassis;

    private String model;

    private String brand;

    private Integer cylinderSize;

    @JsonProperty("isNew")
    private Boolean isNew;

    private Integer mileageKm;

    private BigDecimal purchasePrice;

    private BigDecimal sellPrice;

    private Integer quantity;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "LONGTEXT")
    private String image;

    @ManyToMany(mappedBy = "motorcycles")
    private Set<Order> orders = new HashSet<>();

    // Constructors
    public Motorcycle() {
    }

    public Motorcycle(String numChassis, String model, String brand, Integer cylinderSize, Boolean isNew,
                      Integer mileageKm, BigDecimal purchasePrice, BigDecimal sellPrice, Integer quantity, String image) {
        this.numChassis = numChassis;
        this.model = model;
        this.brand = brand;
        this.cylinderSize = cylinderSize;
        this.isNew = isNew;
        this.mileageKm = mileageKm;
        this.purchasePrice = purchasePrice;
        this.sellPrice = sellPrice;
        this.quantity = quantity;
        this.image = image;

    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Use standard boolean accessors for Jackson

    public String getNumChassis() {
        return numChassis;
    }

    public void setNumChassis(String numChassis) {
        this.numChassis = numChassis;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getCylinderSize() {
        return cylinderSize;
    }

    public void setCylinderSize(Integer cylinderSize) {
        this.cylinderSize = cylinderSize;
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public Integer getMileageKm() {
        return mileageKm;
    }

    public void setMileageKm(Integer mileageKm) {
        this.mileageKm = mileageKm;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }


}