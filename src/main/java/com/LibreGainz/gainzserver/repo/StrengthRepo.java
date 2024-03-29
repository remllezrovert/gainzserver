package com.LibreGainz.gainzserver.repo;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.LibreGainz.gainzserver.model.Strength;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import org.springframework.jdbc.core.RowMapper;
import java.sql.SQLException;
@Repository
public class StrengthRepo{
    public JdbcTemplate jdbcTemp;

    public JdbcTemplate getJdbcTemp() {
        return jdbcTemp;
    }
    @Autowired
    public void setJdbcTemp(JdbcTemplate jdbcTemp) {
        this.jdbcTemp = jdbcTemp;
    }

    public void save(Strength s){
        String sql = 
        """
        INSERT INTO Workout (id, Template_id, workoutDate, weight, unit, timeArr, tagArr, jsonObject) 
        VALUES (?,?,?,?,?,?::smallint[],?::varchar[],?::jsonb);
        """;
    jdbcTemp.update(sql, 
        s.getId(),
        s.getTemplateId(),
        s.getDate(),
        s.getWeight(),
        s.getUnit(), 
        s.getSet(),
        s.getTags(),
        s.getjStr()
        );
    }

    public List<Strength> findAll(){
        return new ArrayList<Strength>();
    }

}
