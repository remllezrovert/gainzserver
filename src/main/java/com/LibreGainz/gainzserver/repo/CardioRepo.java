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
        String sql = "INSERT INTO Cardio (Workout_id, distance, duration, unit) VALUES (?,?,?,?);";
        jdbcTemp.update(sql, c.getId(), c.getDistance(), c.getTime(), c.getUnit());
    }
    public List<Cardio> findAll(){
        return new ArrayList<Cardio>();
    }
}



