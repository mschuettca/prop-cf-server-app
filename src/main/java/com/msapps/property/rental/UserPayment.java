package com.msapps.property.rental;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserPayment {
    enum PaymentType {Check,WireTransfer,Cash,DirectDeposit,Credit};

    @Id
    private String id;
   
//    private @Id @GeneratedValue Long id;
    private String name;
    private Date date;
    private java.util.Date dateTime;
    private PaymentType paymentType;
    private double amount;
    private double lateFee;
    private double totalPayment;
        
    private UserPayment() {
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
 /*   public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }*/
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public java.util.Date getDateTime() {
        return dateTime;
    }
    public void setDateTime(java.util.Date dateTime) {
        this.dateTime = dateTime;
    }
    public PaymentType getPaymentType() {
        return paymentType;
    }
    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public double getLateFee() {
        return lateFee;
    }
    public void setLateFee(double lateFee) {
        this.lateFee = lateFee;
    }
    public double getTotalPayment() {
        return totalPayment;
    }
    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }
    @Override
    public String toString() {
        return "UserPayment{" +
                "id=" + id +
                ", Name='" + this.name + '\'' +
                ", Date='" + this.date + '\'' +
                ", Type='" + this.paymentType + '\'' +
                ", Amount='" + this.amount + '\'' +
                ", Late Fee='" + this.lateFee + '\'' +
                ", Total Payment='" + this.totalPayment + '\'' +
                '}';
    }
}
