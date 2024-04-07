package com.LibreGainz.gainzserver.repo;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.LibreGainz.gainzserver.model.Strength;
import com.LibreGainz.gainzserver.model.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import org.springframework.jdbc.core.RowMapper;
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
        INSERT INTO Workout (Client_id, id, Template_id, workoutDate, weight, unit, repArr, tagArr) 
        VALUES (?,?,?,?,?,?::Unit,?::smallint[],?::varchar[]);
        """;
    jdbcTemp.update(sql, 
        s.getUserId(),
        s.getId(),
        s.getTemplateId(),
        s.getDate(),
        s.getWeight().getWeight(),
        s.getWeight().getUnit().toString(), 
        s.getSet().toArray(new Short[s.getSet().size()]),
        s.getTags().toArray(new String[s.getTags().size()])
        );
    }

public List<Strength> findAll(){
    String sql = """
        SELECT * 
        FROM workout as W
        INNER JOIN template as T
        ON T.id = W.template_id
        AND T.workoutType = 'Strength';
                """;

    RowMapper<Strength> mapper = (rs, rowNum) ->
        Extract(rs);
    //String myTag = jdbcTemp.query(sql2);
    List<Strength> workoutList = jdbcTemp.query(sql, mapper);
    return workoutList;
    }

/**
 * Convert a single database row into an object
 * @param rs
 * @return Strength
 * @throws SQLException
 */
private Strength Extract(ResultSet rs) throws SQLException {
    Strength s = new Strength(rs.getInt("Template_id"),rs.getLong("id"));
    s.setDate(rs.getDate("workoutDate"));

    String str = rs.getString("tagArr").replace("\\","").replace("\"", "");
    s.setTags(StrParse.toTagArray(str.substring(1,str.length() -1)));

    String repStr = rs.getString("repArr").trim();
    s.setSet(StrParse.toStrengthSet(repStr.substring(1,repStr.length() -1)));
    s.setWeight(StrParse.toWeight(rs.getString("weight") + rs.getString("unit")));

    return s;
    }





}
