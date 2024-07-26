package org.example.todoappjavabackend.model;

public enum TodoStatus {
    OPEN("todo"),
    IN_PROGRESS("doing"),
    DONE("done");

    private final String status;

    TodoStatus(String status) { this.status = status; }

    public String getStatus() { return status; }

}
