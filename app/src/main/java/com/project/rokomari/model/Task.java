package com.project.rokomari.model;

import java.io.Serializable;
import java.util.Set;

public class Task implements Serializable {

    private int id;
    private String taskName;
    private String createdDate;
    private String deadline;
    private String description;
    private int status;
    private String email;
    private String phone;
    private String url;

    public Task() {
    }

    public Task(int id, String taskName, String createdDate, String deadline, String description, int status, String email, String phone, String url) {
        this.id = id;
        this.taskName = taskName;
        this.createdDate = createdDate;
        this.deadline = deadline;
        this.description = description;
        this.status = status;
        this.email = email;
        this.phone = phone;
        this.url = url;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getDescription() {
        return description;
    }

    public int getStatus() {
        return status;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getUrl() {
        return url;
    }
}
