package com.coderoux.presentation.los.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by jroux on 20.06.2016.
 */
@Entity
@Table(name = "ACCOUNT")
public class Account {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Account build(String username, String password) {
        Account account = new Account();
        account.setPassword(password);
        account.setUsername(username);
        return account;
    }
}
