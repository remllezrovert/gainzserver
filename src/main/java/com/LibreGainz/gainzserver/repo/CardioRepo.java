package com.LibreGainz.gainzserver.repo;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.LibreGainz.gainzserver.model.Cardio;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import org.springframework.jdbc.core.RowMapper;
import java.sql.SQLException;
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
        INSERT INTO Workout (id, Template_id, workoutDate, distance, duration, unit, tagArr, jsonObject) 
        VALUES (?,?,?,?,?,?::varchar[],?::jsonb);
        """;
    jdbcTemp.update(sql, 
        c.getId(),
        c.getTemplateId(),
        c.getDate(),
        c.getDistance(),
        c.getTime(), 
        c.getUnit(), 
        c.getTags(),
        c.getjStr()
        );
    }
    public List<Cardio> findAll(){
        return new ArrayList<Cardio>();
    }
}



