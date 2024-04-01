package com.LibreGainz.gainzserver.repo;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.LibreGainz.gainzserver.model.Workout;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.*;
import org.springframework.boot.json.*;
import java.sql.ResultSet;
import org.springframework.jdbc.core.RowMapper;
import java.sql.SQLException;
import org.json.*;
import org.postgresql.util.PGobject;


import com.fasterxml.jackson.databind.ObjectMapper;

//import org.postgresql.util.PGobject;

@Repository

public class WorkoutRepo{
    public JdbcTemplate jdbcTemp;

    public JdbcTemplate getJdbcTemp() {
        return jdbcTemp;
    }
    
    @Autowired
    public void setJdbcTemp(JdbcTemplate jdbcTemp) {
        this.jdbcTemp = jdbcTemp;
    }

    public void save(Workout w){
        String sql = "INSERT INTO Workout (id, Template_id, workoutDate, tagArr, jsonObject) VALUES (?,?,?,?::varchar[],?::jsonb);";
        //This converts ArrayList<String> into json
        String json = "";
        try{
        final ObjectMapper mapper = new ObjectMapper();
        json = mapper.writeValueAsString(w.getTags());
        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
        }
        jdbcTemp.update(sql,
        w.getId(),
        w.getTemplateId(),
        w.getDate(),
        w.getTags().toArray(new String[w.getTags().size()]),
        json
        );
    }

    public List<Workout> findAll(){
    String sql = "SELECT * FROM Workout;";

    RowMapper<Workout> mapper = (rs, rowNum) ->
        extracted(rs);
    //String myTag = jdbcTemp.query(sql2);
    List<Workout> workoutList = jdbcTemp.query(sql, mapper);
    return workoutList;
    }

    private Workout extracted(ResultSet rs) throws SQLException {
        Workout w = new Workout();
        w.setId(rs.getInt("id"));
        w.setDate(rs.getDate("workoutDate"));
        String str = rs.getString("tagArr").replace("\\","").replace("\"", "");
        for (String tag :str.substring(1,str.length() -1).split(","))
            w.addTag(tag);
        return w;
    }


    public List<Workout> getByTag(String tag){
    String sql = "SELECT * FROM Workout WHERE " +"'" + tag + "'" + "=ANY(tagArr);";

    RowMapper<Workout> mapper = (rs, rowNum) ->
        extracted(rs);
    List<Workout> myList= jdbcTemp.query(sql, mapper);
    return myList;
        
    } 

}


