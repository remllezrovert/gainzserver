package com.LibreGainz.gainzserver.model;
import java.sql.Timestamp;
import org.springframework.stereotype.Component;

@Component
public class Device {
    private long id;
    private int userId;
    private Timestamp sync;
    private static User user = new User("tina");

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public Timestamp getSync() {
        return sync;
    }
    public void setSync(Timestamp sync) {
        this.sync = sync;
    }


    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Device.user = user;
    }
    
    




}
