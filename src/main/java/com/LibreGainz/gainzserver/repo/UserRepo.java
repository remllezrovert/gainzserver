package com.LibreGainz.gainzserver.repo;
import com.LibreGainz.gainzserver.model.Unit;
import com.LibreGainz.gainzserver.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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

    public void save(User user) throws DuplicateKeyException
    {
        System.out.println("added");
        String sql = """
            INSERT INTO Client (title,dateFormatStr,longDistanceUnit,weightUnit)
            VALUES (?,?,?::Unit,?::Unit);
            """;
        jdbcTemp.update(
            sql, user.getName(),
            user.getDateFormatStr(),
            user.getLongDistanceUnit().toString(),
            user.getWeightUnit().toString()
            );
    
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

     public List<User> find(String name) 
     {
        String sql = "SELECT * FROM Client WHERE title LIKE '" + name + "';";
        RowMapper<User> mapper = (rs, rowNum) ->
            {
            User u = new User(rs.getString("title"));
            u.setId(rs.getInt("id"));
            u.setDateFormatStr(rs.getString("dateformatstr"));
            u.setLongDistanceUnit(Unit.valueOf(rs.getString("longdistanceunit")));
            u.setWeightUnit(Unit.valueOf(rs.getString("weightunit")));
            return u;
            };


        List<User> userList = jdbcTemp.query(sql, mapper);
        return userList;
    }


     public List<User> find(int id){
        String sql = "SELECT * FROM Client WHERE id = " + String.valueOf(id) +";";
        RowMapper<User> mapper = (rs, rowNum) ->
            {
            User u = new User(rs.getString("title"));
            u.setId(rs.getInt("id"));
            return u;
            };

        List<User> userList = jdbcTemp.query(sql, mapper);
        return userList;
    }







    public boolean delete(Integer userId){
        Object[] args = new Object[]{userId};
        String sql = """
            DELETE FROM client 
            where id = ?;
                """;
            return jdbcTemp.update(sql,args) == 1;
    }
 


}

