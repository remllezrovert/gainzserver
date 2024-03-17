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
        String sql = "INSERT INTO Strength (Workout_id, weight, unit, repArr) VALUES (?,?,?,?);";
        jdbcTemp.update(sql,s.getId(),s.getWeight(),s.getUnit(),s.getSet());
    }
    public List<Strength> findAll(){
        return new ArrayList<Strength>();
    }

}

