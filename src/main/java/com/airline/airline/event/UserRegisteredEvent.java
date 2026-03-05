package com.airline.airline.event;

public class UserRegisteredEvent {

    private String email;
    private String name;

    public UserRegisteredEvent() {
    }

    public UserRegisteredEvent(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() { return email; }
    public String getName() { return name; }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }
}