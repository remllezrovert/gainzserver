package com.libregainz.server.repo;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.libregainz.server.model.*;
import com.libregainz.server.repo.*;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.ResultSet;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

@Repository

public class ExerciseRepo{
    public JdbcTemplate jdbcTemp;
    private TemplateRepo templateRepo;

    public ExerciseRepo (){
    templateRepo = new TemplateRepo();
    }


  private String mapToHstore(Map<?, ?> map) {
    StringBuilder hstoreString = new StringBuilder();
    for (Map.Entry<?, ?> entry : map.entrySet()) {
        if (hstoreString.length() > 0) {
            hstoreString.append(", ");
        }
        String key;
        if (
            entry.getKey() instanceof Weight ||
            entry.getKey() instanceof Distance 
        ) {
            key = entry.getKey().toString();
        } else {
            key = entry.getKey().toString().replace("\"", "\\\"");
        }
        hstoreString.append("\"").append(key).append("\" => ");
        hstoreString.append("\"").append(entry.getValue().toString().replace("\"", "\\\"")).append("\"");
    }
    return hstoreString.toString();
}      


    private Object[] prepareParams(Exercise e){
    // Prepare the parameters
    Object[] params = new Object[] {
        e.getTemplateId(),
        e.getClientId(),
        e.getDate()
    };
    
    Object data = e.getData("content");

    if (data instanceof Map) {
        params[3] = mapToHstore((Map<?,?>) data);
    } else if (data instanceof JsonNode){
        params[3] = data.toString();
    } else if (data instanceof byte[]){
        params[3] = data;
    } else {
        throw new IllegalArgumentException(
        "Unsupported data type " + 
        data.getClass().getSimpleName());
    }
    return params;
    } 

    public Exercise extract(ResultSet rs){
        try {
        Exercise e = new Exercise();
        
        e.setId(rs.getLong("id"));
        e.setTemplateId(rs.getInt("Template_id"));
        e.setData("content", rs.getObject("content"));
        e.setClientId(rs.getInt("Client_id"));
        e.setDate(rs.getDate("dateValue"));
        return e;
        }
        catch (SQLException se){
            System.out.println("SQL Exception has occured in TemplateRepo");
            return null;
        }
    }

public void save(Exercise e){
String sql = """
    WITH new_exercise AS (
        INSERT INTO exercise (Template_id, Client_id, dateValue) 
        VALUES (?, ?, ?::date)
        RETURNING id
    )
    INSERT INTO %s (Exercise_id, content)
    SELECT id, ? FROM new_exercise
    """.formatted(
        templateRepo.find(e.getTemplateId()).get(0).getDataType().toString()
    );
   jdbcTemp.update(sql, prepareParams((e)));
   }



public List<Exercise> findAll(){
    String sql = """
        SELECT * 
        FROM exercise as E
        INNER JOIN Strength as S
        ON E.id = S.Exercise_id
        INNER JOIN Isometric as I
        ON E.id = I.Exercise_id
        INNER JOIN Cardio as C
        ON E.id = C.Exercise_id
        INNER JOIN JsonObject as J
        ON E.id = J.Exercise_id
        INNER JOIN Blob as B
        ON E.id = B.Exercise_id
        ;
                """;

    RowMapper<Exercise> mapper = (rs, rowNum) ->
    {
        return extract(rs);
    };

    List<Exercise> exerciseList = jdbcTemp.query(sql, mapper);
    return exerciseList;
    }



public List<Exercise> findAll(int clientId, int limit){
    String sql = """
        SELECT * 
        FROM exercise as E
        INNER JOIN Strength as S
        ON E.id = S.Exercise_id
        INNER JOIN Isometric as I
        ON E.id = I.Exercise_id
        INNER JOIN Cardio as C
        ON E.id = C.Exercise_id
        INNER JOIN JsonObject as J
        ON E.id = J.Exercise_id
        INNER JOIN Blob as B
        ON E.id = B.Exercise_id
        WHERE E.client_id =
                """ + clientId +" LIMIT " + limit + ";";

    RowMapper<Exercise> mapper = (rs, rowNum) ->
    {
        Exercise exercise = extract(rs);
        return exercise;
    };
    List<Exercise> exerciseList = jdbcTemp.query(sql, mapper);
    return exerciseList;
    }






public List<Exercise> findAll(int clientId, Date startDate, Date endDate, int limit){
    Object[] args = new Object[]{clientId, startDate, endDate, limit};
    String sql = """
        SELECT * 
        FROM exercise as E
        INNER JOIN Strength as S
        ON E.id = S.Exercise_id
        INNER JOIN Isometric as I
        ON E.id = I.Exercise_id
        INNER JOIN Cardio as C
        ON E.id = C.Exercise_id
        INNER JOIN JsonObject as J
        ON E.id = J.Exercise_id
        INNER JOIN Blob as B
        ON E.id = B.Exercise_id
        WHERE E.client_id = ?
        AND E.dateValue >= ?
        AND E.dateValue <= ?
        LIMIT ?
        ;
                """;

    RowMapper<Exercise> mapper = (rs, rowNum) ->
    {
        Exercise exercise = extract(rs);
        return exercise;
    };
    List<Exercise> exerciseList = jdbcTemp.query(sql,mapper, args);
    return exerciseList;
    }




public List<Exercise> findAll(int clientId, int templateId, Date startDate, Date endDate, int limit){
    Object[] args = new Object[]{templateId, clientId, startDate, endDate, limit};
    String sql = """
        SELECT * 
        FROM exercise as E
        INNER JOIN Strength as S
        ON E.id = S.Exercise_id
        INNER JOIN Isometric as I
        ON E.id = I.Exercise_id
        INNER JOIN Cardio as C
        ON E.id = C.Exercise_id
        INNER JOIN JsonObject as J
        ON E.id = J.Exercise_id
        INNER JOIN Blob as B
        ON E.id = B.Exercise_id
        AND T.id = ?  
        WHERE E.client_id = ?
        AND E.dateValue >= ?
        AND E.dateValue <= ?
        LIMIT ?
        ;
                """;

    RowMapper<Exercise> mapper = (rs, rowNum) ->
    {
        Exercise exercise = extract(rs);
        return exercise;
    };




    List<Exercise> exerciseList = jdbcTemp.query(sql, mapper, args);
    return exerciseList;
    }









public List<Exercise> find(long id ){
    String sql = """
        SELECT * 
        FROM exercise as E
        INNER JOIN Strength as S
        ON E.id = S.Exercise_id
        INNER JOIN Isometric as I
        ON E.id = I.Exercise_id
        INNER JOIN Cardio as C
        ON E.id = C.Exercise_id
        INNER JOIN JsonObject as J
        ON E.id = J.Exercise_id
        INNER JOIN Blob as B
        ON E.id = B.Exercise_id
        AND E.id =
        """ + String.valueOf(id) + ";";

    RowMapper<Exercise> mapper = (rs, rowNum) ->
    {
        Exercise exercise = extract(rs);
        return exercise;
    };



    List<Exercise> exerciseList = jdbcTemp.query(sql, mapper);
    return exerciseList;
    }






 public boolean update(Integer clientId, Exercise e){
    String tableName = templateRepo.find(e.getTemplateId()).get(0).getDataType().toString();
    String sql = 
    """
    WITH updated_exercise AS (
        UPDATE exercise
        SET Template_id = ?, Client_id = ?, dateValue = ?::date
        WHERE id = ?
        RETURNING id
    )
    UPDATE %s
    SET content = ?
    WHERE Exercise_id = (SELECT id FROM updated_exercise);
    """.formatted(tableName);
    return jdbcTemp.update(sql, prepareParams(e)) == 1;
}
}