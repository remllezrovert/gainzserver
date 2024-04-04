package com.LibreGainz.gainzserver.repo;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.LibreGainz.gainzserver.model.Isometric;
import com.LibreGainz.gainzserver.model.*;
import net.sf.jsqlparser.expression.TimeValue;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import org.springframework.jdbc.core.RowMapper;
import java.sql.SQLException;
import java.sql.Time;
import java.time.format.*;
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
        VALUES (?,?,?,?,?::Unit,?::time[],?::varchar[],?::jsonb);
        """;
    jdbcTemp.update(sql, 
        i.getId(),
        i.getTemplateId(),
        i.getDate(),
        i.getWeight().getWeight(),
        i.getWeight().getUnit().toString(), 
        i.getSet().toArray(new String[i.getSet().size()]),
        i.getTags().toArray(new String[i.getTags().size()]),
        i.getjStr()
        );
    }

    public List<Isometric> findAll(){
        return new ArrayList<Isometric>();
    }
/**
 * Convert a single database row into an object
 * @param rs
 * @return Isometric
 * @throws SQLException
 */
private Isometric Extract(ResultSet rs) throws SQLException {
    Isometric s = new Isometric(rs.getInt("Template_id"),rs.getLong("id"));
    s.setDate(rs.getDate("workoutDate"));

    String str = rs.getString("tagArr").replace("\\","").replace("\"", "");
    s.setTags(StrParse.toTagArray(str.substring(1,str.length() -1)));

    String repStr = rs.getString("repArr").trim();
    s.setSet(StrParse.toIsometricSet(repStr.substring(1,repStr.length() -1)));
    s.setWeight(StrParse.toWeight(rs.getString("weight") + rs.getString("unit")));

    return s;
    }





}
