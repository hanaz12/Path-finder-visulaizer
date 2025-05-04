package com.example.pathfinder.Entity;

public class Response {
    private int cost;
    private boolean hasPath;

    public Response(int cost, boolean hasPath) {
        this.cost = cost;
        this.hasPath = hasPath;
    }

    // Getters يجب أن تكون بنفس الأسماء المستخدمة في الكود
    public int getCost() {
        return cost;
    }

    public boolean isHasPath() {
        return hasPath;
    }

    // Setters
    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setHasPath(boolean hasPath) {
        this.hasPath = hasPath;
    }
}