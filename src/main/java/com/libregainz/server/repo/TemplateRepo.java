package com.libregainz.server.repo;
import com.libregainz.server.model.DataType;
import com.libregainz.server.model.Template;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.ArrayList;
import java.util.List;

import java.io.*;
import java.sql.ResultSet;
import org.springframework.jdbc.core.RowMapper;
import java.sql.SQLException;

@Repository
public class TemplateRepo{
    public JdbcTemplate jdbcTemp;

    public JdbcTemplate getJdbcTemp() {
        return jdbcTemp;
    }
    @Autowired
    public void setJdbcTemp(JdbcTemplate jdbcTemp) {
        this.jdbcTemp = jdbcTemp;
    }

    public void save(Template t) throws DuplicateKeyException
    {
        String sql = """
        INSERT INTO Template (Client_id, title, fieldArray,summary) 
        VALUES (?,?,?,?);
        """;
        jdbcTemp.update(sql,
        t.getClientId(),
        t.getTitle(),
        t.getDataType(),
        t.getSummary()
        );
    }

public boolean update(Integer clientId, Template t) throws DuplicateKeyException
{
        String sql = """
        UPDATE Template SET 
        Client_id = ?,
        title = ?,
        fieldArray = ?, 
        summary = ?
        WHERE id = ?
        AND client_id = ?;
        """;
        return jdbcTemp.update(sql,
        t.getClientId(),
        t.getTitle(),
        t.getDataType(),
        t.getSummary(),
        t.getId(),
        clientId
        ) == 1;
    }




    public Template extract(ResultSet rs){
        try {
        Template t = new Template(rs.getInt("id"));
        t.setDataType(DataType.valueOf(rs.getString("dataType")));
        t.setTitle(rs.getString("title"));
        t.setSummary(rs.getString("summary"));
        t.setClientId(rs.getInt("Client_id"));
        return t;
        }
        catch (SQLException se){
            System.out.println("SQL Exception has occured in TemplateRepo");
            return null;
        }
    }

    public List<Template> findAll(){
    String sql = "SELECT * FROM Template;";
    RowMapper<Template> mapper = (rs, rowNum) ->
        extract(rs);
        List<Template> templateList= jdbcTemp.query(sql, mapper);
        return templateList;
    }

public List<Template> findAll(int clientId, int limit){
    String sql = """
        SELECT * 
        FROM Template
        WHERE client_id =
                """ + clientId +" LIMIT " + limit + ";";
    RowMapper<Template> mapper = (rs, rowNum) ->
    extract(rs);
    List<Template> exerciseList = jdbcTemp.query(sql, mapper);
    return exerciseList;
    }

public List<Template> find(int templateId){
    String sql = """
        SELECT * 
        FROM Template
        WHERE client_id =
                """ + String.valueOf(templateId) + ";";
    RowMapper<Template> mapper = (rs, rowNum) ->
        extract(rs);
    List<Template> exerciseList = jdbcTemp.query(sql, mapper);
    return exerciseList;
    }







 public boolean delete(Integer id){
        Object[] args = new Object[]{id};
        String sql = """
            DELETE FROM template 
            where id = ?;
                """;
            return jdbcTemp.update(sql,args) == 1;
    }
        public boolean delete(Integer clientId, Integer id){
        Object[] args = new Object[]{id, clientId};
        String sql = """
            DELETE FROM template 
            WHERE id = ?
            AND Client_id = ?;
                """;
            return jdbcTemp.update(sql,args) == 1;
    }




}