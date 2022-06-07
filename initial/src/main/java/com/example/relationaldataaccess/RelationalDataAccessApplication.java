package com.example.relationaldataaccess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SpringBootApplication
public class RelationalDataAccessApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(RelationalDataAccessApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RelationalDataAccessApplication.class, args);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... strings) throws Exception {
        log.info("Creating tables...");

        String[] names = new String[]{"John Woo", "Jeff Dean", "Josh Bloch", "Josh Long"};

        jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE customers(id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");

        List<Object[]> splitNames = Arrays.stream(names).map(name -> name.split(" ")).collect(Collectors.toList());
        jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?,?)", splitNames);

        log.info("select Josh:");
        jdbcTemplate.query(
                "SELECT id, first_name, last_name FROM customers WHERE first_name = ?",
                (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name")),
                new Object[]{"Josh"}
        ).forEach(customer -> log.info(customer.toString()));

        log.info("select by id=1 with CustomerResultSet:");
        CustomerResultSet customerResultSet = new CustomerResultSet();
        List<Customer> list = jdbcTemplate.query(
                "SELECT id, first_name, last_name FROM customers WHERE id = ?",
                customerResultSet,
                1
        );
        Objects.requireNonNull(list).forEach(System.out::println);
//        без generic? в CustomerResultSet unsafe operation


        log.info("select by id=2 with CustomerRowMapper:");
        CustomerRowMapper rowMapper = new CustomerRowMapper();
        jdbcTemplate.query(
                "SELECT id, first_name, last_name FROM customers WHERE id = ?",
                rowMapper,
                new Object[]{2}
        ).forEach(customer -> log.info(customer.toString()));
//        без generic? в CustomerRowMapper unsafe operation

        log.info("select by id=3 with CustomerRowCallback:");
        CustomerRowCallback customerRowCallback = new CustomerRowCallback();
        jdbcTemplate.query(
                "SELECT id, first_name, last_name FROM customers WHERE id = ?",
                customerRowCallback,
                3
        );
    }

}
