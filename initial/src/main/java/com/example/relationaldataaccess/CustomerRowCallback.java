package com.example.relationaldataaccess;

import org.springframework.jdbc.core.RowCallbackHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRowCallback implements RowCallbackHandler {
    @Override
    public void processRow(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        System.out.println("ResultSet. id: " + id);
        System.out.println("ResultSet. first_name: " + firstName);
        System.out.println("ResultSet. last_name: " + lastName);


    }
}
