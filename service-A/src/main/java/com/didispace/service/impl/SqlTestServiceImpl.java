package com.didispace.service.impl;

import com.didispace.service.SqlTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Created by henryzhou on 17/2/17.
 */

@Service
public class SqlTestServiceImpl implements SqlTestService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public String getList() {
        String sql = "select * from CA_Product";
        List<String> list = (List<String>) jdbcTemplate.query(sql, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("ProductCode") + "    " + rs.getString("ProductName") + "/n";
            }
        });
        return list.toString();
    }

    @Override
    public String update() {
        String sql = "INSERT INTO CA_MainOrder VALUES(?,?,?,?,?,?)";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, "123123123");
                ps.setString(2, "10");
                ps.setString(3,"123123");
                ps.setString(4, "123123");
                ps.setTimestamp(5,new Timestamp(System.currentTimeMillis()));
                ps.setTimestamp(6,new Timestamp(System.currentTimeMillis()));

                return ps;
            }
        },holder);
        return holder.getKey().toString();
    }
}
