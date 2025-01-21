package com.amicus.dz25_sqlite;

public class Item {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String name;
    private String author;
    private int imageResId;
    private boolean choice;

    public boolean isChoice() {
        return choice;
    }

    public void setChoice(boolean choice) {
        this.choice = choice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public Item(int id, int imageResId, String name, String author) {
        this.id = id;
        this.imageResId = imageResId;
        this.name = name;
        this.author = author;
        this.choice=false;
    }
}
