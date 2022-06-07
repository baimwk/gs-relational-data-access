package com.example.relationaldataaccess;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerResultSet implements ResultSetExtractor<List<Customer>> {
    @Override
    public List<Customer> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Customer> list = new ArrayList<>();
        while (rs.next()) {
            long id = rs.getLong("id");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            list.add(new Customer(id, firstName, lastName));
        }
        return list;
    }
}
