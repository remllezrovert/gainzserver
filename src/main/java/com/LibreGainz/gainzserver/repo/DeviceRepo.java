package com.LibreGainz.gainzserver.repo;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.LibreGainz.gainzserver.model.Device;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import org.springframework.jdbc.core.RowMapper;
import java.sql.SQLException;
//TODO: Finish writing the sync post method


@Repository
public class DeviceRepo {
    public JdbcTemplate jdbcTemp;

    public JdbcTemplate getJdbcTemp() {
        return jdbcTemp;
    }
    @Autowired
    public void setJdbcTemp(JdbcTemplate jdbcTemp) {
        this.jdbcTemp = jdbcTemp;
    }

    public void save(Device d){

        String sql = "INSERT INTO Device (id, User_id, sync) VALUES (?,?,?);";
        jdbcTemp.update(sql,d.getId(), d.getUserId(), d.getSync());
    }

    public void sync(){
        String sql = "UPDATE Device SET sync";
    }

public List<Device> findAll(){
        String sql = "SELECT * FROM Device;";
        RowMapper<Device> mapper = (rs, rowNum) ->
            {
            Device d = new Device();
            d.setId(rs.getLong("id"));
            d.setUserId(rs.getInt("User_id"));
            d.setSync(rs.getTimestamp("sync"));
            return d;
            };
        List<Device> deviceList = jdbcTemp.query(sql, mapper);
        return deviceList;
        }
}
