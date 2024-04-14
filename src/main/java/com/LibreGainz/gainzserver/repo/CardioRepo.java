package com.LibreGainz.gainzserver.repo;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.LibreGainz.gainzserver.model.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import org.springframework.jdbc.core.RowMapper;
import java.sql.*;

@Repository
public class CardioRepo{
    public JdbcTemplate jdbcTemp;

    public JdbcTemplate getJdbcTemp() {
        return jdbcTemp;
    }
    @Autowired
    public void setJdbcTemp(JdbcTemplate jdbcTemp) {
        this.jdbcTemp = jdbcTemp;
    }

    public void save(Cardio c){
    String sql = 
        """
        INSERT INTO Workout (Client_id, Template_id, workoutDate, distance, durration, unit, tagArr) 
        VALUES (?,?,?,?,?,?::Unit,?::varchar[]);
        """;
    jdbcTemp.update(sql, 
        c.getUserId(),
        c.getTemplateId(),
        c.getDate(),
        c.getDistance(),
        c.getTime(), 
        c.getUnit().toString(), 
        c.getTags().toArray(new String[c.getTags().size()])
        );
    }

    public List<Cardio> findAll(){
    String sql = """
    SELECT W.id,template_id, workoutDate, distance, unit, durration, tagArr
    FROM Workout AS W
    INNER JOIN Template AS T
    ON T.id = W.template_id
    AND T.workoutType = 'Cardio';
    """;
    RowMapper<Cardio> mapper = (rs, rowNum) ->
        new Cardio(rs);
    //String myTag = jdbcTemp.query(sql2);
    List<Cardio> workoutList = jdbcTemp.query(sql, mapper);
    return workoutList;
    }
    public List<Cardio> findAll(int userId, int limit){
        String sql = """
            SELECT * 
            FROM workout as W
            INNER JOIN template as T
            ON T.id = W.template_id
            AND T.workoutType = 'Cardio'
            WHERE W.client_id =
                    """ + userId +" LIMIT " + limit + ";";
        RowMapper<Cardio> mapper = (rs, rowNum) ->
            new Cardio(rs);
        List<Cardio> workoutList = jdbcTemp.query(sql, mapper);
        return workoutList;
        }





}



