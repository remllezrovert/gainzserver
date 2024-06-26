package com.LibreGainz.gainzserver.repo;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
import java.sql.Date;
import java.time.format.*;



@Repository
public class IsometricRepo{
    private UserRepo userRepo;
    public IsometricRepo(UserRepo userRepo){
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

    public void save(Isometric i) 
    {
        String sql = 
        """
        INSERT INTO Workout (Client_id, Template_id, workoutDate, weight, unit, timeArr, tagArr) 
        VALUES (?,?,?,?,?::Unit,?::time[],?::varchar[]);
        """;
    jdbcTemp.update(sql, 
        i.getUserId(),
        i.getTemplateId(),
        i.getDate(),
        i.getWeight().getWeight(),
        i.getWeight().getUnit().toString(), 
        i.getSet().toArray(new Time[i.getSet().size()]),
        i.getTags().toArray(new String[i.getTags().size()])
        );
    }

    public boolean update(Integer userId, Isometric i){
        String sql = 
        """
        UPDATE Workout SET 
        Client_id = ?,
        Template_id = ?,
        workoutDate = ?,
        weight = ?,
        unit = ?::Unit,
        timeArr = ?::time[],
        tagArr = ?::varchar[]
        WHERE id = ?
        AND Client_id = ?;
        """;
    return jdbcTemp.update(sql, 
        i.getUserId(),
        i.getTemplateId(),
        i.getDate(),
        i.getWeight().getWeight(),
        i.getWeight().getUnit().toString(), 
        i.getSet().toArray(new Time[i.getSet().size()]),
        i.getTags().toArray(new String[i.getTags().size()]),
        i.getId(),
        userId
        ) == 1;
    }



public List<Isometric> findAll(){
    String sql = """
    SELECT *
    FROM Workout AS W
    INNER JOIN Template AS T
    ON T.id = W.template_id
    AND T.workoutType = 'Isometric';
    """;
    RowMapper<Isometric> mapper = (rs, rowNum) ->
    {
        Isometric isometric = new Isometric(rs);
        isometric.setUser(userRepo.find(rs.getInt("client_id")).get(0));
        return isometric;
    };

    List<Isometric> workoutList = jdbcTemp.query(sql, mapper);
    return workoutList;

}

public List<Isometric> findAll(int userId, int limit){
    String sql = """
        SELECT * 
        FROM workout as W
        INNER JOIN template as T
        ON T.id = W.template_id
        AND T.workoutType = 'Isometric'
        WHERE W.client_id =
                """ + userId +" LIMIT " + limit + ";";
    RowMapper<Isometric> mapper = (rs, rowNum) ->
    {
        Isometric isometric = new Isometric(rs);
        isometric.setUser(userRepo.find(rs.getInt("client_id")).get(0));
        return isometric;
    };


    List<Isometric> workoutList = jdbcTemp.query(sql, mapper);
    return workoutList;
}

 public List<Isometric> find(Long id){
        String sql = """
            SELECT * 
            FROM workout as W
            INNER JOIN template as T
            ON T.id = W.template_id
            AND T.workoutType = 'Isometric'
            WHERE W.id =
                    """ + String.valueOf(id);

    RowMapper<Isometric> mapper = (rs, rowNum) ->
    {
        Isometric isometric = new Isometric(rs);
        isometric.setUser(userRepo.find(rs.getInt("client_id")).get(0));
        return isometric;
    };
        List<Isometric> workoutList = jdbcTemp.query(sql, mapper);
        return workoutList;
        }


public List<Isometric> findAll(int userId, Date startDate, Date endDate, int limit){
    Object[] args = new Object[]{userId, startDate, endDate, limit};
    String sql = """
        SELECT * 
        FROM workout as W
        INNER JOIN template as T
        ON T.id = W.template_id
        AND T.workoutType = 'Isometric'
        WHERE W.client_id = ?
        AND W.workoutDate >= ?
        AND w.workoutDate <= ?
        LIMIT ?
        ;
                """;

    RowMapper<Isometric> mapper = (rs, rowNum) ->
    {
        Isometric isometric = new Isometric(rs);
        isometric.setUser(userRepo.find(rs.getInt("client_id")).get(0));
        return isometric;
    };
    List<Isometric> workoutList = jdbcTemp.query(sql,mapper, args);
    return workoutList;
    }





public List<Isometric> findAll(int userId, int templateId, Date startDate, Date endDate, int limit){
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

    RowMapper<Isometric> mapper = (rs, rowNum) ->
    {
        Isometric isometric = new Isometric(rs);
        isometric.setUser(userRepo.find(rs.getInt("client_id")).get(0));
        return isometric;
    };

    List<Isometric> workoutList = jdbcTemp.query(sql, mapper, args);
    return workoutList;
    }


        




}




