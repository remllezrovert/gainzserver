package com.libregainz.server.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Template {

private int id;
private String title;
private DataType dataType;
private String summary;
private int clientId;
private int formId;

public Template(int id){
    this.id = id;
}
public String getTitle() {
    return title;
}
public void setTitle(String title) {
    this.title = title;
}

public int getId() {
    return id;
}

public void setId(int id) {
    this.id = id;
}

public String getSummary() {
    return summary;
}
public void setSummary(String summary) {
    this.summary = summary;
}
public DataType getDataType() {
    return dataType;
}
public void setDataType(DataType dataType) {
    this.dataType = dataType;
}

public Integer getClientId() {
    return clientId;
}
public void setClientId(int clientId) {
    this.clientId = clientId;
}

public int getFormId() {
    return formId;
}

public void setFormId(int formId) {
    this.formId = formId;
}


}