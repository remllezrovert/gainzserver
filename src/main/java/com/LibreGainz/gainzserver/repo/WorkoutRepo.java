package com.LibreGainz.gainzserver.repo;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.LibreGainz.gainzserver.model.Workout;
import com.LibreGainz.gainzserver.repo.UserRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//import org.postgresql.util.PGobject;

@Repository

public class WorkoutRepo{

    private UserRepo userRepo;    
    WorkoutRepo(UserRepo userRepo){
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

    public void save(Workout w){
        String sql = 
        """
        INSERT INTO Workout (Client_id, id, Template_id, workoutDate, tagArr) 
        VALUES (?,?,?,?,?::varchar[]);
        """;
        //This converts ArrayList<String> into json
        String json = "";
        try{
        final ObjectMapper mapper = new ObjectMapper();
        json = mapper.writeValueAsString(w.getTags());
        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
        }
        jdbcTemp.update(sql,
        w.getUserId(),
        w.getId(),
        w.getTemplateId(),
        w.getDate(),
        w.getTags().toArray(new String[w.getTags().size()]),
        json
        );
    }

    RowMapper<Workout> mapper = (rs, rowNum) ->
        new Workout(rs);


    public boolean update(Integer userId, Workout w){
    String sql = 
    """
    UPDATE Workout SET
    Client_id = ?,
    Template_id = ?,
    workoutDate = ?,
    tagArr = ?::varchar[]
    WHERE id = ?
    AND client_id = ?;
    """;
    return jdbcTemp.update(sql, 
    w.getUserId(),
    w.getTemplateId(),
    w.getDate(),
    w.getTags().toArray(new String[w.getTags().size()]),
    w.getId(),
    userId
    ) == 1;
}






    public List<Workout> find(long id ){
    String sql = """
        SELECT * 
        FROM workout as W
        INNER JOIN template as T
        ON T.id = W.template_id
        AND w.id =
        """ + String.valueOf(id) + ";";

    RowMapper<Workout> mapper = (rs, rowNum) ->
    {
        Workout workout= new Workout(rs);
        workout.setUser(userRepo.find(rs.getInt("client_id")).get(0));
        return workout;
    };
    List<Workout> workoutList = jdbcTemp.query(sql, mapper);
    return workoutList;
}
















    public List<Workout> findAll(){
    String sql = """
        SELECT * FROM Workout AS W;
                """;
    List<Workout> workoutList = jdbcTemp.query(sql, mapper);
    return workoutList;
    }



    public List<Object> findDownCast (String workoutType){

    String sql = """
        SELECT * FROM Workout AS W
        INNER JOIN Template AS T
        ON T.id = W.Template_id
        AND T.workoutType = '
                """ + workoutType + "';";

    RowMapper<Object> downer = (rs, rowNum) ->
        new Workout(rs);
    List<Object> workoutList = jdbcTemp.query(sql, downer);
    return workoutList;

    }




        public boolean delete(Integer id){
        Object[] args = new Object[]{id};
        String sql = """
            DELETE FROM workout
            where id = ?;
                """;
            return jdbcTemp.update(sql,args) == 1;
    }
        public boolean delete(Integer userId, Integer id){
        Object[] args = new Object[]{id, userId};
        String sql = """
            DELETE FROM workout
            WHERE id = ?
            AND Client_id = ?;
                """;
            return jdbcTemp.update(sql,args) == 1;
    }



    public List<Workout> getByTag(String tag){
    String sql = "SELECT * FROM Workout WHERE " +"'" + tag + "'" + "=ANY(tagArr);";
    List<Workout> myList= jdbcTemp.query(sql, mapper);
    return myList;
    } 
    public List<Workout> getByMatch(String col, String searchStr){
    String sql = "SELECT * FROM Workout WHERE " + "'" + col + "' = '" + searchStr + "'";
    List<Workout> myList= jdbcTemp.query(sql, mapper);
    return myList;
    }
     public List<Workout> getBySearch(String col, String searchStr){
    String sql = "SELECT * FROM Workout WHERE " + "'" + col + "' LIKE '" + searchStr + "'";
    List<Workout> myList= jdbcTemp.query(sql, mapper);
    return myList;
    }






    public List<Workout> getByMatchUser(int userId, String col, String searchStr){
    String sql = "SELECT * FROM Workout WHERE " +
    "'" + col + "' = '" + searchStr +
    "' AND Client_id = '" + userId + "';";
    List<Workout> myList= jdbcTemp.query(sql, mapper);
    return myList;
    }
    public List<Workout> getBySearchUser(int userId, String col, String searchStr){
    String sql = "SELECT * FROM Workout WHERE " + 
    "'" + col + "' LIKE '" + searchStr + 
    "' AND Client_id = '" + userId + "';";
    List<Workout> myList= jdbcTemp.query(sql, mapper);
    return myList;
    }
    public List<Workout> getByTagUser(int userId, String tag){
    String sql = "SELECT * FROM Workout WHERE " 
    +"'" + tag + "'" + "=ANY(tagArr)"  +
    " AND Client_id = '" + userId + "';";
    List<Workout> myList= jdbcTemp.query(sql, mapper);
    return myList;
    } 


    



}


