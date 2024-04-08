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
        String sql = 
        """
        INSERT INTO Workout (Client_id, id, Template_id, workoutDate, tagArr) 
        VALUES (?,?,?,?,?::varchar[]);
        """;
        //This converts ArrayList<String> into json
        String json = "";
        try{
        final ObjectMapper mapper = new ObjectMapper();
        json = mapper.writeValueAsString(w.getTags());
        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
        }
        jdbcTemp.update(sql,
        w.getUserId(),
        w.getId(),
        w.getTemplateId(),
        w.getDate(),
        w.getTags().toArray(new String[w.getTags().size()]),
        json
        );
    }

    RowMapper<Workout> mapper = (rs, rowNum) ->
        new Workout(rs);

    
    public List<Workout> findAll(){
    String sql = """
        SELECT * FROM Workout AS W;
                """;
    List<Workout> workoutList = jdbcTemp.query(sql, mapper);
    return workoutList;
    }



    public List<Object> findDownCast (String workoutType){

    String sql = """
        SELECT * FROM Workout AS W
        INNER JOIN Template AS T
        ON T.id = W.Template_id
        AND T.workoutType = '
                """ + workoutType + "';";

    RowMapper<Object> downer = (rs, rowNum) ->
        new Workout(rs);
    List<Object> workoutList = jdbcTemp.query(sql, downer);
    return workoutList;

    }

    public List<Workout> getByTag(String tag){
    String sql = "SELECT * FROM Workout WHERE " +"'" + tag + "'" + "=ANY(tagArr);";
    List<Workout> myList= jdbcTemp.query(sql, mapper);
    return myList;
    } 
    public List<Workout> getByMatch(String col, String searchStr){
    String sql = "SELECT * FROM Workout WHERE " + "'" + col + "' = '" + searchStr + "'";
    List<Workout> myList= jdbcTemp.query(sql, mapper);
    return myList;
    }
     public List<Workout> getBySearch(String col, String searchStr){
    String sql = "SELECT * FROM Workout WHERE " + "'" + col + "' LIKE '" + searchStr + "'";
    List<Workout> myList= jdbcTemp.query(sql, mapper);
    return myList;
    }






    public List<Workout> getByMatchUser(int userId, String col, String searchStr){
    String sql = "SELECT * FROM Workout WHERE " +
    "'" + col + "' = '" + searchStr +
    "' AND Client_id = '" + userId + "';";
    List<Workout> myList= jdbcTemp.query(sql, mapper);
    return myList;
    }
    public List<Workout> getBySearchUser(int userId, String col, String searchStr){
    String sql = "SELECT * FROM Workout WHERE " + 
    "'" + col + "' LIKE '" + searchStr + 
    "' AND Client_id = '" + userId + "';";
    List<Workout> myList= jdbcTemp.query(sql, mapper);
    return myList;
    }
    public List<Workout> getByTagUser(int userId, String tag){
    String sql = "SELECT * FROM Workout WHERE " 
    +"'" + tag + "'" + "=ANY(tagArr)"  +
    " AND Client_id = '" + userId + "';";
    List<Workout> myList= jdbcTemp.query(sql, mapper);
    return myList;
    } 


    



}


