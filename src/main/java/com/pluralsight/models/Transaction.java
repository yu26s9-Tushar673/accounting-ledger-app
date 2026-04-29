package com.pluralsight.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction
{
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private float price;

    /** Constructor method of a Transaction Object */
    public Transaction(LocalDate date, LocalTime time, String description, String vendor, float price) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.price = price;
    }

    /** Displays a Transaction Object */
    public String formattedTransaction(){
        return String.format("\nDate: %s | Time: %s | %s | %s | Amount: $%.2f",
                this.getDate(), this.getTime(), this.getDescription(), this.getVendor(), this.getPrice());
    }

    /** Getter and Setter methods */
    public LocalDate getDate()
    {   return date;    }

    public void setDate(LocalDate date)
    {   this.date = date;   }

    public LocalTime getTime()
    {   return time;    }

    public void setTime(LocalTime time)
    {   this.time = time;   }

    public String getDescription()
    {   return description; }

    public void setDescription(String description)
    {   this.description = description; }

    public String getVendor()
    {   return vendor;  }

    public void setVendor(String vendor)
    {   this.vendor = vendor;   }

    public float getPrice()
    {   return price;   }

    public void setPrice(float price)
    {   this.price = price; }
}
