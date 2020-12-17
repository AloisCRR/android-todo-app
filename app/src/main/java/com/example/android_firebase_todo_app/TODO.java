package com.example.android_firebase_todo_app;

import com.google.firebase.Timestamp;

public class TODO {

    private String title;
    private String content;
    private boolean completed;
    private Timestamp created;
    private Timestamp updated;
    private String user;

    public TODO(String title, String content, boolean completed, Timestamp created, Timestamp updated, String user) {
        this.title = title;
        this.content = content;
        this.completed = completed;
        this.created = created;
        this.updated = updated;
        this.user = user;
    }

    @Override
    public String toString() {
        return "TODO{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", completed=" + completed +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }

    public TODO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public String getUser() { return user; }

    public void setUser(String user) { this.user = user; }

}
