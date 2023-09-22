package org.example.model;

public class Budget {
    private int id;
    private int user_id;
    private String budget_name;

    public Budget() {
    }

    public Budget(int id, int user_id, String budget_name) {
        this.id = id;
        this.user_id = user_id;
        this.budget_name = budget_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getBudget_name() {
        return budget_name;
    }

    public void setBudget_name(String budget_name) {
        this.budget_name = budget_name;
    }
}
