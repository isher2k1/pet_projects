package com.example.demo1;

import java.util.Date;

public class Payment {
    private String serviceName;
    private double amount;
    private Date date;

    public Payment(String serviceName, double amount, Date date) {
        this.serviceName = serviceName;
        this.amount = amount;
        this.date = date;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }
}
