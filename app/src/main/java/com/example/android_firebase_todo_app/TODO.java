package com.example.android_firebase_todo_app;

import com.google.firebase.Timestamp;

public class TODO {

    private String title;
    private String content;
    private boolean completed;
    private Timestamp created;
    private Timestamp updated;

    public TODO(String title, String content, boolean completed, Timestamp created, Timestamp updated) {
        this.title = title;
        this.content = content;
        this.completed = completed;
        this.created = created;
        this.updated = updated;
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

    public boolean getIsCompleted() {
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
}
