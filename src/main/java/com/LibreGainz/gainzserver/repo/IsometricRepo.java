package com.LibreGainz.gainzserver.repo;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.LibreGainz.gainzserver.model.Isometric;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import org.springframework.jdbc.core.RowMapper;
import java.sql.SQLException;
@Repository
public class IsometricRepo{
    public JdbcTemplate jdbcTemp;

    public JdbcTemplate getJdbcTemp() {
        return jdbcTemp;
    }
    @Autowired
    public void setJdbcTemp(JdbcTemplate jdbcTemp) {
        this.jdbcTemp = jdbcTemp;
    }

    public void save(Isometric i){
        String sql = 
        """
        INSERT INTO Workout (id, Template_id, workoutDate, weight, unit, timeArr, tagArr, jsonObject) 
        VALUES (?,?,?,?,?,?::time[],?::varchar[],?::jsonb);
        """;
    jdbcTemp.update(sql, 
        i.getId(),
        i.getTemplateId(),
        i.getDate(),
        i.getWeight(),
        i.getUnit(), 
        i.getSet(),
        i.getTags(),
        i.getjStr()
        );
    }

    public List<Isometric> findAll(){
        return new ArrayList<Isometric>();
    }

}
