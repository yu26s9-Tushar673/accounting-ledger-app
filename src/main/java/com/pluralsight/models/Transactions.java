package com.pluralsight.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transactions
{
    private DateTimeFormatter date;
    private DateTimeFormatter time;
    private String description;
    private String vendor;
    private float price;

    public Transactions(DateTimeFormatter date, DateTimeFormatter time, String description, String vendor, float price) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.price = price;
    }

    public DateTimeFormatter getDate()
    {   return date;    }

    public void setDate(DateTimeFormatter date) {
        this.date = date;
    }

    public DateTimeFormatter getTime() {
        return time;
    }

    public void setTime(DateTimeFormatter time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor()
    {   return vendor;  }

    public void setVendor(String vendor)
    {   this.vendor = vendor;   }

    public float getPrice()
    {   return price;   }

    public void setPrice(float price)
    {   this.price = price; }
}
