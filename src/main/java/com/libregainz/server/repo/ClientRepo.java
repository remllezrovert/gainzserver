package com.libregainz.server.repo;

import com.libregainz.server.model.Client;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.ArrayList;
import java.util.List;



import java.io.*;
import java.sql.ResultSet;
import org.springframework.jdbc.core.RowMapper;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Repository
public class ClientRepo{
    public JdbcTemplate jdbcTemp;

    public JdbcTemplate getJdbcTemp() {
        return jdbcTemp;
    }
    @Autowired
    public void setJdbcTemp(JdbcTemplate jdbcTemp) {
        this.jdbcTemp = jdbcTemp;
    }

    public void save(Client c) throws DuplicateKeyException
    {
        String sql = """
        INSERT INTO client (title,email,clientPassword,verificationCode,verificationExpire,clientEnabled) 
        VALUES (?,?,?,?,?,?);
        """;
        jdbcTemp.update(
            sql,
            c.getTitle(),
            c.getEmail(),
            c.getPassword(),
            c.getVerificationCode(),
            c.getVerificationExpire(),
            c.getEnabled()
        );
    }

public boolean update(Client c) throws DuplicateKeyException
{
        String sql = """
        UPDATE Client SET 
        title = ?,
        email = ?,
        clientPassword = ?,
        verificationCode = ?,
        verificationExpire = ?,
        clientEnabled = ?
        WHERE id = ?
        """;
        return jdbcTemp.update(sql,
            c.getTitle(),
            c.getEmail(),
            c.getPassword(),
            c.getVerificationCode(),
            c.getVerificationExpire(),
            c.getEnabled(),
            c.getId()
        ) == 1;
    }




    public Client extract(ResultSet rs){
        try {
       Client c = new Client(rs.getInt("Id"));
        c.setId(rs.getInt("id"));
        c.setTitle(rs.getString("title"));
        c.setEmail(rs.getString("email"));
        c.setEnabled(rs.getBoolean("clientEnabled"));
        c.setPassword(rs.getString("clientPassword"));
        c.setVerificationCode(rs.getString("verificationCode"));
        c.setVerificationExpire(rs.getObject("verificationExpire", LocalDateTime.class));
        return c;
        }
        catch (SQLException se){
            System.out.println("SQL Exception has occured in ClientRepo");
            return null;
        }
    }

    public List<Client> findAll(){
    String sql = "SELECT * FROM Client;";
    RowMapper<Client> mapper = (rs, rowNum) ->
        extract(rs);
        List<Client> clientList= jdbcTemp.query(sql, mapper);
        System.out.println(clientList.size());
        return clientList;
    }

public List<Client> findAll(int userId, int limit){
    String sql = """
        SELECT * 
        FROM Client
        WHERE id =
                """ + userId +" LIMIT " + limit + ";";
    RowMapper<Client> mapper = (rs, rowNum) ->
    extract(rs);
    List<Client> clientList = jdbcTemp.query(sql, mapper);
    return clientList;
    }

public List<Client> find(int Id){
    String sql = """
        SELECT * 
        FROM Client
        WHERE id =
                """ + String.valueOf(Id) + ";";
    RowMapper<Client> mapper = (rs, rowNum) ->
        extract(rs);
    List<Client> clientList = jdbcTemp.query(sql, mapper);
    return clientList;
    }


public List<Client> find(String title){
    String sql = """
        SELECT * 
        FROM Client
        WHERE title = '""" 
        + String.valueOf(title) + "';";
    RowMapper<Client> mapper = (rs, rowNum) ->
        extract(rs);
    List<Client> clientList = jdbcTemp.query(sql, mapper);
    return clientList;
    }









 public boolean delete(Integer Id){
        Object[] args = new Object[]{Id};
        String sql = """
            DELETE FROM client 
            where id = ?;
                """;
            return jdbcTemp.update(sql,args) == 1;
    }
        public boolean delete(Integer clientId , Integer id){
        Object[] args = new Object[]{id};
        String sql = """
            DELETE FROM client 
            WHERE id = ?
                """;
            return jdbcTemp.update(sql,args) == 1;
    }

    public Client findByEmail(String email) {
    String sql = """
        SELECT * 
        FROM Client
        WHERE email = '""" 
        + String.valueOf(email) + "';";
    RowMapper<Client> mapper = (rs, rowNum) ->
        extract(rs);
    List<Client> clientList = jdbcTemp.query(sql, mapper);
    return clientList.get(0);
    }

    public Client findByTitle(String title) {
        System.out.println("\n\n\n" + title + "\n\n\n");
    String sql = """
        SELECT * 
        FROM gainzdb.client
        WHERE title = '""" 
        + String.valueOf(title) + "';";
    RowMapper<Client> mapper = (rs, rowNum) ->
        extract(rs);
    List<Client> clientList = jdbcTemp.query(sql, mapper);
    return clientList.get(0);
    }


}