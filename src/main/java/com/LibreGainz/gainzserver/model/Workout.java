package com.LibreGainz.gainzserver.model;
import java.sql.Date;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import java.util.ArrayList;


@Component
@Primary
public class Workout {
    private ArrayList<String> tags = new ArrayList<String>();
    private long id;
    private long templateId;
    private Date date;
    private String jStr;


    public Date getDate(){
        return date;
    }
    public void setDate(Date newDate){
        date = newDate;
    }
    public long getId(){
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(long templateId) {
        this.templateId = templateId;
    }

    public ArrayList<String> getTags() {
        return tags;
    }
    
    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public void addTag(String tag){
        tags.add(tag);
    }

    public String getjStr() {
        return jStr;
    }

    public void setjStr(String jStr) {
        this.jStr = jStr;
    }


}
