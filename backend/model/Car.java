package com.rideease.democar_rental.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carid")
    private Long carId;

    @NotEmpty(message = "Car model cannot be empty.")
    @Column(name = "CarModel")
    private String carModel;

    @Min(value = 2000, message = "Year must be 2000 or later.")
    @Column(name = "year")
    private int year;

    @Positive(message = "Price must be a positive number.")
    @Column(name = "price")
    private double price;

    @NotEmpty(message = "Color cannot be empty.")
    @Column(name = "color")
    private String color;

    @Column(name = "status")
    private String status;


    public Long getCarId() { return carId; }
    public void setCarId(Long carId) { this.carId = carId; }
    public String getCarModel() { return carModel; }
    public void setCarModel(String carModel) { this.carModel = carModel; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}