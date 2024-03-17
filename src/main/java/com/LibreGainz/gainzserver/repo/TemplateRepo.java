package com.LibreGainz.gainzserver.repo;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.LibreGainz.gainzserver.model.Template;
import java.util.ArrayList;
import java.util.List;

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

    public void save(Template t){
        String sql = "INSERT INTO Template (id, Client_id, title, description) VALUES (?,?,?,?);";
        jdbcTemp.update(sql,t.getId(), t.getUserId(), t.getName(),t.getDesc());
    }

    public List<Template> findAll(){
    String sql = "SELECT * FROM Template;";
    RowMapper<Template> mapper = (rs, rowNum) ->
        {
        Template t = new Template();
        t.setId(rs.getInt("id"));
        t.setUserId(rs.getInt("User_id"));
        t.setName(rs.getString("title"));
        t.setDesc(rs.getString("description"));
        return t;
        };
        List<Template> templateList= jdbcTemp.query(sql, mapper);
        return templateList;
    }



}
