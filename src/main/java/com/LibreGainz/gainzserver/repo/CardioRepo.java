package com.LibreGainz.gainzserver.repo;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.LibreGainz.gainzserver.model.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import org.springframework.jdbc.core.RowMapper;
import java.sql.SQLException;
/**
 * TODO: Write an Extract method for this class
 */
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
        INSERT INTO Workout (id, Template_id, workoutDate, distance, durration, unit, tagArr, jsonObject) 
        VALUES (?,?,?,?,?,?::Unit,?::varchar[],?::jsonb);
        """;
    jdbcTemp.update(sql, 
        c.getId(),
        c.getTemplateId(),
        c.getDate(),
        c.getDistance(),
        c.getTime(), 
        c.getUnit().toString(), 
        c.getTags().toArray(new String[c.getTags().size()]),
        c.getjStr()
        );
    }

    public List<Cardio> findAll(){
    String sql = """
    SELECT W.id,template_id, workoutDate, distance, unit, durration, tagArr, W.jsonObject
    FROM Workout AS W
    INNER JOIN Template AS T
    ON T.id = W.template_id
    AND T.workoutType = 'Cardio';
    """;
    RowMapper<Cardio> mapper = (rs, rowNum) ->
        Extract(rs);
    //String myTag = jdbcTemp.query(sql2);
    List<Cardio> workoutList = jdbcTemp.query(sql, mapper);
    return workoutList;
    }


/**
 * Convert a single database row into an object
 * @param rs
 * @return Cardio
 * @throws SQLException
 */
private Cardio Extract(ResultSet rs) throws SQLException {
    Cardio c = new Cardio(rs.getInt("Template_id"),rs.getLong("id"));
    c.setDate(rs.getDate("workoutDate"));

    String str = rs.getString("tagArr").replace("\\","").replace("\"", "");
    c.setTags(StrParse.toTagArray(str.substring(1,str.length() -1)));

    c.setDistance(Math.round(rs.getFloat("distance") * 1000.0) / 1000.0); 
    //c.setDistance(rs.getFloat("distance"));
    c.setUnit(Unit.valueOf(rs.getString("unit")));
    try{
    c.setTime(StrParse.toTime(rs.getString("durration")));
    } catch(NullPointerException npe) {

    }

    return c;
    }



}



