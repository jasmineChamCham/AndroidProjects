package com.example.burgermenu;

import android.content.Intent;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Order extends Intent {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    private String name;
    @ColumnInfo
    private boolean prosciutto;
    @ColumnInfo
    private boolean cheese; // true - asiago, false - creme fraiche
    @ColumnInfo
    private int sauceSpoon;
    @ColumnInfo
    private float price;
    @ColumnInfo
    private String status;

    public Order(int id, String name, boolean prosciutto, boolean cheese, int sauceSpoon, float price, String status){
        this.id = id;
        this.name = name;
        this.prosciutto = prosciutto;
        this.cheese = cheese;
        this.sauceSpoon = sauceSpoon;
        this.price = price;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isProsciutto() {
        return prosciutto;
    }

    public void setProsciutto(boolean prosciutto) {
        this.prosciutto = prosciutto;
    }

    public boolean isCheese() {
        return cheese;
    }

    public void setCheese(boolean cheese) {
        this.cheese = cheese;
    }

    public int getSauceSpoon() {
        return sauceSpoon;
    }

    public void setSauceSpoon(int sauceSpoon) {
        this.sauceSpoon = sauceSpoon;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", prosciutto=" + prosciutto +
                ", cheese=" + cheese +
                ", sauceSpoon=" + sauceSpoon +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }
}
