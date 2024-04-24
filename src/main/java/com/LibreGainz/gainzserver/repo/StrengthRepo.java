package com.LibreGainz.gainzserver.repo;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.LibreGainz.gainzserver.model.Strength;
import com.LibreGainz.gainzserver.model.*;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import org.springframework.jdbc.core.RowMapper;
@Repository
public class StrengthRepo{
    UserRepo userRepo;
    public StrengthRepo(UserRepo userRepo){
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

    public void save(Strength s){
        String sql = 
        """
        INSERT INTO Workout (Client_id, Template_id, workoutDate, weight, unit, repArr, tagArr) 
        VALUES (?,?,?,?,?::Unit,?::smallint[],?::varchar[]);
        """;
    jdbcTemp.update(sql, 
        s.getUserId(),
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
    {
        Strength strength = new Strength(rs);
        strength.setUser(userRepo.find(rs.getInt("client_id")).get(0));
        return strength;
    };

    List<Strength> workoutList = jdbcTemp.query(sql, mapper);
    return workoutList;
    }



public List<Strength> findAll(int userId, int limit){
    String sql = """
        SELECT * 
        FROM workout as W
        INNER JOIN template as T
        ON T.id = W.template_id
        AND T.workoutType = 'Strength'
        WHERE W.client_id =
                """ + userId +" LIMIT " + limit + ";";

    RowMapper<Strength> mapper = (rs, rowNum) ->
    {
        Strength strength = new Strength(rs);
        strength.setUser(userRepo.find(rs.getInt("client_id")).get(0));
        return strength;
    };




    List<Strength> workoutList = jdbcTemp.query(sql, mapper);
    return workoutList;
    }






public List<Strength> findAll(int userId, Date startDate, Date endDate, int limit){
    Object[] args = new Object[]{userId, startDate, endDate, limit};
    String sql = """
        SELECT * 
        FROM workout as W
        INNER JOIN template as T
        ON T.id = W.template_id
        AND T.workoutType = 'Strength'
        WHERE W.client_id = ?
        AND W.workoutDate >= ?
        AND w.workoutDate <= ?
        LIMIT ?
        ;
                """;

    RowMapper<Strength> mapper = (rs, rowNum) ->
    {
        Strength strength = new Strength(rs);
        strength.setUser(userRepo.find(rs.getInt("client_id")).get(0));
        return strength;
    };
    List<Strength> workoutList = jdbcTemp.query(sql,mapper, args);
    return workoutList;
    }




public List<Strength> findAll(int userId, int templateId, Date startDate, Date endDate, int limit){

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

    RowMapper<Strength> mapper = (rs, rowNum) ->
    {
        Strength strength = new Strength(rs);
        strength.setUser(userRepo.find(rs.getInt("client_id")).get(0));
        return strength;
    };




    List<Strength> workoutList = jdbcTemp.query(sql, mapper, args);
    return workoutList;
    }









public List<Strength> find(int id ){
    String sql = """
        SELECT * 
        FROM workout as W
        INNER JOIN template as T
        ON T.id = W.template_id
        AND T.workoutType = 'Strength'
        AND w.id =
        """ + String.valueOf(id) + ";";

    RowMapper<Strength> mapper = (rs, rowNum) ->
    {
        Strength strength = new Strength(rs);
        strength.setUser(userRepo.find(rs.getInt("client_id")).get(0));
        return strength;
    };



    List<Strength> workoutList = jdbcTemp.query(sql, mapper);
    return workoutList;
    }

 public boolean update(Integer userId, Strength s){
    String sql = 
    """
    UPDATE Workout SET
    Client_id = ?,
    Template_id = ?,
    workoutDate = ?,
    weight = ?,
    unit = ?::Unit,
    repArr = ?::smallint[],
    tagArr = ?::varchar[]
    WHERE id = ?
    AND client_id = ?;
    """;
    return jdbcTemp.update(sql, 
    s.getUserId(),
    s.getTemplateId(),
    s.getDate(),
    s.getWeight().getWeight(),
    s.getWeight().getUnit().toString(), 
    s.getSet().toArray(new Short[s.getSet().size()]),
    s.getTags().toArray(new String[s.getTags().size()]),
    s.getId(),
    userId
    ) == 1;
}







}
