package com.example.roompractice.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "owner")
public class Owner {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ownerId")
    private Long id;

    @ColumnInfo(name = "user_name")
    private String userName;

    @ColumnInfo(name = "level")
    private String level;

    public Owner(Long id, String userName, String level) {
        this.id = id;
        this.userName = userName;
        this.level = level;
    }

    @Ignore
    public Owner(String userName, String level) {
        this.userName = userName;
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
