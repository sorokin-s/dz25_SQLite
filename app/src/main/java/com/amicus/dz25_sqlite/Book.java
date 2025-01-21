package com.amicus.dz25_sqlite;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "booksDB")
public class Book {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String author;
    public int imageResId;

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public boolean isChoice() {
        return choice;
    }

    public void setChoice(boolean choice) {
        this.choice = choice;
    }

    public boolean choice;

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", imageResId=" + imageResId +
                ", choice=" + choice +
                '}';
    }

    public Book(int imageResId, String name, String author) {
        this.name = name;
        this.author = author;
        this.imageResId = imageResId;
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
}
