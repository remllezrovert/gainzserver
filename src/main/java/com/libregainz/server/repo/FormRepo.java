package com.libregainz.server.repo;
import com.libregainz.server.model.Form;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class FormRepo {

    private final JdbcTemplate jdbcTemp;

    public FormRepo(JdbcTemplate jdbcTemp) {
        this.jdbcTemp= jdbcTemp;
    }

    public void save(Form form) {
        String sql = "INSERT INTO Form (content) VALUES (?);";
        jdbcTemp.update(sql,form.getContent());
    }

    public Optional<Form> findById(int id) {
        String sql = "SELECT * FROM Form WHERE id = ?";
        return jdbcTemp.query(sql, new FormRowMapper(), id).stream().findFirst();
    }

    private static class FormRowMapper implements RowMapper<Form> {
        @Override
        public Form mapRow(ResultSet rs, int rowNum) throws SQLException {
            Form form = new Form();
            form.setId(rs.getInt("id"));
            form.setContent(rs.getBytes("content"));
            return form;
        }
    }
}
