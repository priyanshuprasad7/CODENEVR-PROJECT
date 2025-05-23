package model;

import java.util.Date;

public class Expense {
    private int id;
    private String title;
    private double amount;
    private String category;
    private String notes;
    private Date date; // Added date field

    // Default constructor
    public Expense() {
    }

    // Parameterized constructor
    public Expense(int id, String title, double amount, String category, String notes, Date date) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.notes = notes;
        this.date = date;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", amount=" + amount +
                ", category='" + category + '\'' +
                ", notes='" + notes + '\'' +
                ", date=" + date +
                '}';
    }
}
