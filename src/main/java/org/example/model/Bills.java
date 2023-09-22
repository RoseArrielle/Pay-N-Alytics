package org.example.model;

import java.math.BigDecimal;

public class Bills {
    private int id;
    private int budget_Id;
    private String name;
    private Double amount;

    public Bills() {
    }

    public Bills(int id, int budget_Id, String name, Double amount) {
        this.id = id;
        this.budget_Id = budget_Id;
        this.name = name;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBudget_Id() {
        return budget_Id;
    }

    public void setBudget_Id(int budget_Id) {
        this.budget_Id = budget_Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
