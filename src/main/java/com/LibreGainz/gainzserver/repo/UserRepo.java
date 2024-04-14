package com.LibreGainz.gainzserver.repo;
import com.LibreGainz.gainzserver.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import org.springframework.jdbc.core.RowMapper;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepo {

    private JdbcTemplate jdbcTemp;

    public JdbcTemplate getJdbcTemp() {
        return jdbcTemp;
    }

    @Autowired
    public void setJdbcTemp(JdbcTemplate jdbcTemp) {
        this.jdbcTemp= jdbcTemp;
    }


    public void save(User user){
        System.out.println("added");
        String sql = "INSERT INTO Client (title) VALUES (?);";
        jdbcTemp.update(sql, user.getName());
    }

    public List<User> findAll(){
        String sql = "SELECT * FROM Client;";
        RowMapper<User> mapper = (rs, rowNum) ->
            {
            User u = new User(rs.getString("title"));
            u.setId(rs.getInt("id"));
            return u;
            };

        List<User> userList = jdbcTemp.query(sql, mapper);
        return userList;
    }

}

