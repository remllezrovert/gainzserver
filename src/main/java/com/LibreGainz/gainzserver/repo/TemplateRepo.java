package com.LibreGainz.gainzserver.repo;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.LibreGainz.gainzserver.model.Template;
import java.util.ArrayList;
import java.util.List;

import java.io.*;
import java.sql.ResultSet;
import org.springframework.jdbc.core.RowMapper;
import java.sql.SQLException;

@Repository
public class TemplateRepo{
    public JdbcTemplate jdbcTemp;

    public JdbcTemplate getJdbcTemp() {
        return jdbcTemp;
    }
    @Autowired
    public void setJdbcTemp(JdbcTemplate jdbcTemp) {
        this.jdbcTemp = jdbcTemp;
    }

    public void save(Template t){
        String sql = """
        INSERT INTO Template (Client_id, title, workoutType,summary) 
        VALUES (?,?,?,?);
        """;
        jdbcTemp.update(sql,
        t.getUserId(),
        t.getName(),
        t.getWorkoutType(),
        t.getDesc()
        );
    }

    public Template Extract(ResultSet rs){
        Template t = new Template();
        try {
        t = new Template(rs.getInt("id"));
        t.setWorkoutType(rs.getString("workoutType"));
        t.setName(rs.getString("title"));
        t.setDesc(rs.getString("summary"));
        }
        catch (SQLException se){
            System.out.println("SQL Exception has occured in TemplateRepo");
            }
        return t;
    }

    public List<Template> findAll(){
    String sql = "SELECT * FROM Template;";
    RowMapper<Template> mapper = (rs, rowNum) ->
        Extract(rs);
        List<Template> templateList= jdbcTemp.query(sql, mapper);
        return templateList;
    }

public List<Template> findAll(int userId, int limit){
    String sql = """
        SELECT * 
        FROM Template
        WHERE client_id =
                """ + userId +" LIMIT " + limit + ";";
    RowMapper<Template> mapper = (rs, rowNum) ->
        new Template(rs);
    List<Template> workoutList = jdbcTemp.query(sql, mapper);
    return workoutList;
    }





}
