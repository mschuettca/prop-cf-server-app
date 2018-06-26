package com.msapps.property.rental;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
    enum PaymentType {Annual,Monthly,BiMonthly};

    @Id
    private String id;

//    private @Id @GeneratedValue Long id;
    private String name;
    private String description;
    private double share;
    private PaymentType paymentType;
    private int firstMonthOfPayment;
    private double paymentLimit; 
    private int gracePeriodDays;
        
    private User() {
    }
    public User(String name, double share, PaymentType type, int gracePeriod, double paymentLimit, String description) {
        this.name = name;
        this.share = share;
        this.paymentType = type;
        this.gracePeriodDays = gracePeriod;
        this.firstMonthOfPayment = Calendar.JANUARY; //First month in the year for bi-monthly payments
        this.paymentLimit = paymentLimit;
        this.description = description;
    }    
 /*   public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }*/
    public String getId() {
    return id;
}
public void setId(String id) {
    this.id = id;
}
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getShare() {
        return share;
    }
    public void setShare(double share) {
        this.share = share;
    }
    public PaymentType getPaymentType() {
        return paymentType;
    }
    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
    public int getFirstMonthOfPayment() {
        return firstMonthOfPayment;
    }
    public void setFirstMonthOfPayment(int month) {
        this.firstMonthOfPayment = month;
    }
    public double getPaymentLimit() {
        return paymentLimit;
    }
    public void setPaymentLimit(double paymentLimit) {
        this.paymentLimit = paymentLimit;
    }
    public int getGracePeriodDays() {
        return gracePeriodDays;
    }
    public void setGracePeriodDays(int gracePeriodDays) {
        this.gracePeriodDays = gracePeriodDays;
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", Name='" + this.name + '\'' +
                ", Share='" + this.share + '\'' +
                ", Payment Type='" + this.paymentType + '\'' +
                ", Payment Start='" + this.firstMonthOfPayment + '\'' +
                ", Payment Limit='" + this.getPaymentLimit() + '\'' +
                ", Grace Period='" + this.gracePeriodDays + " days" + '\'' +
                ", Description"
                + ""
                + ""
                + "='" + this.description + '\'' +
                '}';
    }
}
