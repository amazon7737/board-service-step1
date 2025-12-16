package org.example.application.Auth;

public class Authorities {
    private final Long id;
    private final String name;

    public Authorities(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
