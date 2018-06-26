package com.msapps.property.rental;

//import java.util.Date;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Transaction {
    enum Type {Credit,Debit,CREstate,DBEstate,Deposit};
    enum Category {RentalIncome,Insurance,Repairs,Mortgage,SecurityDeposit,GarbageService,WaterService,PropertyTax,MISC,ElectricService,GasService};

    @Id
    private String id;

  //  private @Id @GeneratedValue Long id;
    private Date date;
    private java.util.Date dateTime;
    private String payee;
    private String description;
    private double cost;
    private Type type;
    private Category category;
        
    private Transaction() {
    }
    public Transaction(Date date, double cost, Type type, Category category, String payee, String description) {
        this.date = date;
        this.cost = cost;
        this.type = type;
        this.category = category;
        this.payee = payee;
        this.description = description;
    }
    public Transaction(double cost, Type type, Category category, String payee, String description) {
        this.date = new Date(System.currentTimeMillis());
        this.cost = cost;
        this.type = type;
        this.category = category;
        this.payee = payee;
        this.description = description;
    }
    public String getId() {
        return id;
    }
 /*  public Long getId() {
        return id;
    }*/
 /*   public String getId() {
        return id;
    }*/
    public void setDateTime(java.util.Date dateTime) {
        this.dateTime = dateTime;
    }
    public java.util.Date getDateTime() {
        return this.dateTime;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public Date getDate() {
        return this.date;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }
    public Type getType() {
        return this.type;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public Category getCategory() {
        return this.category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public double getCost() {
        return this.cost;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return this.description;
    }
    public void setPayee(String payee) {
        this.payee = payee;
    }
    public String getPayee() {
        return this.payee;
    }
    
    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", Date='" + this.date + '\'' +
                ", Type='" + this.type + '\'' +
                ", Category='" + this.category + '\'' +
                ", Cost='" + this.cost + '\'' +
                ", Description"
                + ""
                + ""
                + "='" + this.description + '\'' +
                '}';
    }
}
