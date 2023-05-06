package com.example.contactapplication;

import android.content.Intent;
import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import kotlin.text.Charsets;

@Entity
public class Contact extends Intent {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo
    public String firstName;
    @ColumnInfo
    public String lastName;
    @ColumnInfo
    public String phone;
    @ColumnInfo
    public String email;
    @ColumnInfo
    public String home;
    @ColumnInfo
    public String avatar;

    public Contact(String firstName, String lastName, String phone, String email, String home, String avatar) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.home = home;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getHome() {
        return home;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "firstName=" + firstName +
                ", lastName=" + lastName +
                ", phone=" + phone +
                ", email=" + email +
                ", home=" + home +
                '}';
    }
}
