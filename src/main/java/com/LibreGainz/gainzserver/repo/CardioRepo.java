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
    private UserRepo userRepo;
    public CardioRepo(UserRepo userRepo){
        this.userRepo = userRepo;
    }
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



public boolean update(Integer userId, Cardio c){
    String sql = 
        """
        UPDATE Workout SET
        Client_id = ?,
        Template_id = ?,
        workoutDate = ?,
        distance = ?,
        durration = ?,
        unit = ?::Unit,
        tagArr = ? ::varchar[]
        WHERE id = ?
        AND client_id = ?;
        """;
    return jdbcTemp.update(sql, 
        c.getUserId(),
        c.getTemplateId(),
        c.getDate(),
        c.getDistance(),
        c.getTime(), 
        c.getUnit().toString(), 
        c.getTags().toArray(new String[c.getTags().size()]),
        c.getId(),
        userId
        ) == 1;
    }







    public List<Cardio> findAll(){
    String sql = """
    SELECT *
    FROM Workout AS W
    INNER JOIN Template AS T
    ON T.id = W.template_id
    AND T.workoutType = 'Cardio';
    """;

    RowMapper<Cardio> mapper = (rs, rowNum) ->
    {
        Cardio cardio = new Cardio(rs);
        cardio.setUser(userRepo.find(rs.getInt("client_id")).get(0));
        return cardio;
    };
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
    {
        Cardio cardio = new Cardio(rs);
        cardio.setUser(userRepo.find(rs.getInt("client_id")).get(0));
        return cardio;
    };
    List<Cardio> workoutList = jdbcTemp.query(sql, mapper);
    return workoutList;
    }


 public List<Cardio> find(int id){
        String sql = """
            SELECT * 
            FROM workout as W
            INNER JOIN template as T
            ON T.id = W.template_id
            AND T.workoutType = 'Cardio'
            WHERE W.id =
                    """ + String.valueOf(id);
    RowMapper<Cardio> mapper = (rs, rowNum) ->
    {
        Cardio cardio = new Cardio(rs);
        cardio.setUser(userRepo.find(rs.getInt("client_id")).get(0));
        return cardio;
    };
        List<Cardio> workoutList = jdbcTemp.query(sql, mapper);
        return workoutList;
        }


public List<Cardio> findAll(int userId, Date startDate, Date endDate, int limit){
    Object[] args = new Object[]{userId, startDate, endDate, limit};
    String sql = """
        SELECT * 
        FROM workout as W
        INNER JOIN template as T
        ON T.id = W.template_id
        AND T.workoutType = 'Cardio'
        WHERE W.client_id = ?
        AND W.workoutDate >= ?
        AND w.workoutDate <= ?
        LIMIT ?
        ;
                """;

    RowMapper<Cardio> mapper = (rs, rowNum) ->
    {
        Cardio cardio = new Cardio(rs);
        cardio.setUser(userRepo.find(rs.getInt("client_id")).get(0));
        return cardio;
    };
    List<Cardio> workoutList = jdbcTemp.query(sql,mapper, args);
    return workoutList;
    }




public List<Cardio> findAll(int userId, int templateId, Date startDate, Date endDate, int limit){
    Object[] args = new Object[]{templateId, userId, startDate, endDate, limit};
    String sql = """
        SELECT * 
        FROM workout as W
        INNER JOIN template as T
        ON T.id = W.template_id
        AND T.id = ?  
        WHERE W.client_id = ?
        AND W.workoutDate >= ?
        AND w.workoutDate <= ?
        LIMIT ?
        ;
                """;

    RowMapper<Cardio> mapper = (rs, rowNum) ->
    {
        Cardio cardio = new Cardio(rs);
        cardio.setUser(userRepo.find(rs.getInt("client_id")).get(0));
        return cardio;
    };

    List<Cardio> workoutList = jdbcTemp.query(sql, mapper, args);
    return workoutList;
    }





}



