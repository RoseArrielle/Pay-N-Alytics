package org.example.model;

public class SavedBudget {
    private int id;
    private int user_Id;
    private String budget_Name;
    private String budget_Data;

    public SavedBudget() {
    }

    public SavedBudget(int id, int user_Id, String budget_Name, String budget_Data) {
        this.id = id;
        this.user_Id = user_Id;
        this.budget_Name = budget_Name;
        this.budget_Data = budget_Data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(int user_Id) {
        this.user_Id = user_Id;
    }

    public String getBudget_Name() {
        return budget_Name;
    }

    public void setBudget_Name(String budget_Name) {
        this.budget_Name = budget_Name;
    }

    public String getBudget_Data() {
        return budget_Data;
    }

    public void setBudget_Data(String budget_Data) {
        this.budget_Data = budget_Data;
    }
}
