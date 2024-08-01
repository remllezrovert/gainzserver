package com.libregainz.server.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class Client implements UserDetails{

    private Integer id;
    private String title;
    private String email;
    private String password;
    private boolean enabled;
    private String verificationCode;
    private LocalDateTime verificationExpire;



    public Client(){}
    public Client(int id ){
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getVerificationCode() {
        return verificationCode;
    }
    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
    public LocalDateTime getVerificationExpire() {
        return verificationExpire;
    }
    public void setVerificationExpire(LocalDateTime verificationExpire) {
        this.verificationExpire = verificationExpire;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
        //change this to do role base auth
    }

    @Override
    public String getUsername() {
        return title;
    }
    public void setUsername(String username) {
        this.title = username;
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override 
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override 
    public boolean isCredentialsNonExpired(){
        return true;
    }
    @Override 
    public boolean isEnabled(){
        return enabled;
    }
    public String getEmail() {
        return email;
    }
    public void setEnabled(boolean b) {
        this.enabled = b;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword(){
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }





}
