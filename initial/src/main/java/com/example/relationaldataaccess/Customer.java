package com.example.relationaldataaccess;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer {
    private long id;
    private String firstNane, LastName;

    public Customer(long id, String firstNane, String lastName) {
        this.id = id;
        this.firstNane = firstNane;
        LastName = lastName;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstNane='" + firstNane + '\'' +
                ", LastName='" + LastName + '\'' +
                '}';
    }

}
