package com.doodle.pollservice.domain;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.UUID;

public class Poll {

    public Poll() {

    }

    public Poll(String id, String title, Date createdOn, UUID userId) {
        this.id = id;
        this.title = title;
        this.createdOn = createdOn;
        this.userId = userId;
    }

    @Id
    private String id;
    private String title;
    private Date createdOn;
    private UUID userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
