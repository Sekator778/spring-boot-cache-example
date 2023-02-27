package com.javatpoint.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Customer {
    private int accountNumber;
    private String customerName;
    private String accountType;
    private double balance;
}