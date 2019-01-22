package com.example.linkgame.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class Ranking {
    private String userName;

    @Id(autoincrement = true)
    private Long id;
    private String date;
    private long record;
    private int type;

    @Generated(hash = 693570839)
    public Ranking(String userName, Long id, String date, long record, int type) {
        this.userName = userName;
        this.id = id;
        this.date = date;
        this.record = record;
        this.type = type;
    }

    public Ranking(String userName, long record, int type, String date) {
        this.userName = userName;
        this.date = date;
        this.record = record;
        this.type = type;
    }

    @Generated(hash = 1361760905)
    public Ranking() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getRecord() {
        return record;
    }

    public void setRecord(long record) {
        this.record = record;
    }
}
