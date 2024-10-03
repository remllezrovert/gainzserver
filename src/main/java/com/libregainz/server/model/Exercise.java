package com.libregainz.server.model;

import com.libregainz.server.repo.ExerciseRepo;
import com.libregainz.server.repo.TemplateRepo;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.stereotype.Component;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.sql.*;
import java.io.*;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Exercise {

private HashMap<String, Object> dataMap;
private long id;
private int clientId;
private int templateId;
private Date date;

public Exercise(Long id){
    this.id = id;
}

public Exercise(){
}

public long getId() {
    return id;
}
public void setId(long id) {
    this.id = id;
}
public int getClientId() {
    return clientId;
}
public void setClientId(int clientId) {
    this.clientId = clientId;
}

public int getTemplateId() {
    return templateId;
}

public void setTemplateId(int templateId) {
    this.templateId = templateId;
}

public Object getData(String label){
    return dataMap.get(label);
}

public HashMap<String, Object> getData(){
    return dataMap;
}

public boolean setData(String label, Object data){
    try {
        dataMap.put(label, data);
        return true;
    } catch(Exception e){
        e.printStackTrace();
        return false;
    }
}

public Date getDate() {
    return date;
}

public void setDate(Date date) {
    this.date = date;
}



}
